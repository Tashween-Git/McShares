package com.mcshares.demo.utils;

import com.mcshares.demo.ValidationResultBean;
import com.mcshares.demo.ValidationResultEnum;
import com.mcshares.demo.adapter.VOMapping;
import com.mcshares.demo.dao.IErrorLogDAO;
import com.mcshares.demo.model.Customer;
import com.mcshares.demo.model.ErrorLog;
import com.mcshares.demo.service.CustomerService;
import com.mcshares.demo.vo.DataItemCustomerVO;
import com.mcshares.demo.vo.RequestDocVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class XmlProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlProcessor.class);

    private String XML_EXTENSION = "xml";

    @Resource
    private VOMapping voMapping;

    @Resource
    private CustomerService customerService;

    @Resource
    private ValidationUtils validationUtils;

    @Resource
    private IErrorLogDAO errorLogDAO;


    @Transactional
    public ValidationResultBean processFile(MultipartFile file) {
        LOGGER.info("START processFile for file: {}", file.getOriginalFilename());
        ValidationResultBean validationResultBean = new ValidationResultBean();

        if (!StringUtils.getFilenameExtension(file.getOriginalFilename()).equalsIgnoreCase(XML_EXTENSION)) {
            LOGGER.info("Extension is not of type {} for file: {}", XML_EXTENSION, file.getOriginalFilename());
            validationResultBean.getValidationResultEnumList().add(ValidationResultEnum.INVALID_EXTENSION);
            return validationResultBean;
        }

        JAXBContext jaxbContext = null;
        try {
            File tempFile = File.createTempFile("TEMP_", LocalDate.now().toString());
            file.transferTo(tempFile);

            validateAgainstXSD(tempFile);

            jaxbContext = JAXBContext.newInstance(RequestDocVO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            RequestDocVO requestDocVO = (RequestDocVO) jaxbUnmarshaller.unmarshal(tempFile);

            validationResultBean.setCountCustomersInFile(requestDocVO.getDocDataVO().getDataItemCustomerVOList().size());
            ErrorLog errorLog = new ErrorLog();
            for (DataItemCustomerVO dataItemCustomerVO : requestDocVO.getDocDataVO().getDataItemCustomerVOList()) {
                boolean isRecordValid = validationUtils.validateAge(dataItemCustomerVO.getDateOfBirth(), errorLog) &&
                        validationUtils.validateNumberOfShares(dataItemCustomerVO.getSharesDetailsVO().getNumShares(), errorLog) &&
                        validationUtils.validateSharePrice(dataItemCustomerVO.getSharesDetailsVO().getSharePrice(), errorLog);

                LOGGER.info("customerRef: {} isRecordValid = {}, ", dataItemCustomerVO.getCustomerId(), isRecordValid);
                if (isRecordValid) {
                    try {
                        persistData(dataItemCustomerVO);
                        int count = validationResultBean.getCountSuccessfulCreation();
                        count++;
                        validationResultBean.setCountSuccessfulCreation(count);
                    } catch (Exception e) {
                        LOGGER.error("Exception occurred: ", e);
                    }
                } else {
                    LOGGER.info("Creating error log for customerRef: {}", dataItemCustomerVO.getCustomerId());
                    errorLog.setCustomerRef(dataItemCustomerVO.getCustomerId());
                    errorLog.setFilename(file.getOriginalFilename());
                    errorLogDAO.create(errorLog);
                }
            }

            if (validationResultBean.getCountCustomersInFile() != validationResultBean.getCountSuccessfulCreation()) {
                validationResultBean.getValidationResultEnumList().add(ValidationResultEnum.PARTIAL_SUCCESS);
            } else {
                validationResultBean.getValidationResultEnumList().add(ValidationResultEnum.SUCCESS);
            }

        } catch (JAXBException | SAXException | IOException e) {
            LOGGER.error("Exception occurred: ", e);
        }

        LOGGER.info("END processFile for file: {}", file.getOriginalFilename());
        return validationResultBean;
    }

    private void persistData(DataItemCustomerVO dataItemCustomerVO) throws Exception {
        Customer customer = voMapping.fromVO(dataItemCustomerVO);
        customerService.addCustomer(customer);
        LOGGER.info("END persistData for customer: {}", customer);
    }

    private void validateAgainstXSD(File tempFile) throws SAXException, IOException {
        LOGGER.info("START validateAgainstXSD for tempFile: {}", tempFile.getName());
        File schemaFile = new File("src\\main\\resources\\mcshares.xsd");
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Source xmlFileSource = new StreamSource(tempFile);

        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        validator.validate(xmlFileSource);

        LOGGER.info("END validateAgainstXSD for tempFile: {}", tempFile.getName());
    }

}
