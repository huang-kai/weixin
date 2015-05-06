package net.home.exception;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide utility message api
 * 
 * @author hukai
 *
 */
public enum ErrCode {

    // Reserved Err Code
    UNKONW_ERROR("9999"),
    NOT_NULL("0001"),
    MONEY_FORMAT_ERROR("0002"),
    DATE_FORMAT_ERROR("0003"),
    COMMAND_NOT_SUPPORT("0004")
    ;

    
    
    private String errCode;
    private static final Logger logger = LoggerFactory.getLogger(ErrCode.class);

    private ErrCode(String code) {
        errCode = code;
    }

    public String getCode() {
        return errCode;
    }

    /**
     * Get error message according to error code
     * 
     * @param defaultMessage
     *            String to return if the lookup fails
     * @return the resolved message if the lookup was successful; otherwise the
     *         default message passed as a parameter
     */
    public String getErrMsg(String defaultMessage) {
        return getErrMsg(null, defaultMessage, null);
    }

    /**
     * Get error message according to error code
     * 
     * @param args
     *            array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}"
     *            within a message), or <code>null</code> if none.
     * @param locale
     *            the Locale in which to do the lookup
     * @return the resolved message
     */
    public String getErrMsg(Object[] args, Locale locale) {
        String message = null;
        String code =getCode();
        if (code != null && !code.equals("")) {
            message = getErrorCodes().getProperty(code);
        }
        if (message == null || message.equals("")) {
            logger.warn("Error code (" + code + ") not found in "  + ERRORCODE_PROPS_FILENAME);
            message = getErrorCodes().getProperty(UNKONW_ERROR.toString());
        }
        try {
            message = MessageFormat.format(message, args);
        } catch (MissingFormatArgumentException mfae) {
            logger.warn("Error message (" + message
                    + ") cannot be properly formatted");
            return message;
        }
        if (message.indexOf("%s") >= 0) {
            logger.warn("Error message (" + message
                    + ") still has replacement variable not being filled in");
        }
        return message;
        
    }

    /**
     * Get error message according to error code
     * 
     * @param locale
     *            the Locale in which to do the lookup
     * @return the resolved message
     */
    public String getErrMsg(Locale locale) {
        return getErrMsg(null, locale);
    }

    /**
     * Get error message according to error code
     * 
     * @param args
     *            array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}"
     *            within a message), or <code>null</code> if none.
     * @return the resolved message
     */
    public String getErrMsg(Object... args) {
        return getErrMsg(args, null);
    }

    /**
     * Get error message according to error code
     * 
     * @return the resolved message
     */
    public String getErrMsg() {
        return getErrMsg(null, null);
    }

    @Override
    public String toString() {
        return super.toString() + ":" + this.getCode();
    }
    
    private static Properties ERRORCODE_PROPS;
    private static final String ERRORCODE_PROPS_FILENAME = "exceptions.properties";
    
    public Properties getErrorCodes() {
        if (ERRORCODE_PROPS != null && !ERRORCODE_PROPS.isEmpty())
            return ERRORCODE_PROPS;

        // Load error codes
//      logger.info("Loading error codes properties...");
        try {
            ERRORCODE_PROPS = new Properties();
            InputStream is = ErrCode.class.getResourceAsStream(ERRORCODE_PROPS_FILENAME);
            ERRORCODE_PROPS.load(is);
        } catch (IOException ioe) {
            logger.error("Unable to load error code properties file: " + ioe.getMessage());
        }
        return ERRORCODE_PROPS;
    }
}
