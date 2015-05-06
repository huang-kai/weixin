package net.home.exception;

import java.util.Locale;

/**
 * General Exception
 * 
 * <p>
 * This class is inherited from RuntimeException in order to avoid mandatory try catch blocks.<br>
 *</p> 
 * 
 * @author hukai
 */
@SuppressWarnings("serial")
public class GeneralException extends RuntimeException {
	
	protected ErrCode code;
	protected Object data;
	/**
	 * args is used for error message which requires array of argument
	 */
	protected Object[] args;
	/**
	 * locale is used for error message
	 */
	protected Locale locale;
	
	
	public GeneralException(ErrCode code) {
		super();
		this.code = code;
	}
	
	public GeneralException(ErrCode code, Object ... args) {
		this(code);
		this.args = args;
	}
	
	
	public GeneralException(ErrCode code, Throwable cause) {
		super(cause);
		this.code = code;
	}
	
	public GeneralException(ErrCode code, Throwable cause, Object ... args) {
		this(code, cause);
		this.args = args;
	}
	
	/**
	 * Return associated data to this exception
	 * @return
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * Set associated data to this exception
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * Return associated code to this exception
	 * @return
	 */
	public String getCode() {
		return code.getCode();
	}
	
	/**
	 * Return associated error message to this exception.
	 * The error messages are current defined/loaded in {@link ErrCode.CODE}
	 * @return
	 */
	public String getErrMsg() {
		if (args != null && locale != null)
			return code.getErrMsg(args, locale);
		else if (args != null) 
			return code.getErrMsg(args);
		else if (locale != null) 
			return code.getErrMsg(locale);
		return code.getErrMsg();
	}
	
	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object ... args) {
		this.args = args;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(" [err_code:" + this.code + ", err_msg:" + getErrMsg() + "]").toString();
	}

	@Override
	public String getMessage() {
		return this.toString();
	}
	
	public static void main(String[] args) {
//        GeneralException ex=new GeneralException(ErrCode.GENERAL_CODE, "aa","bb");
//        System.out.println(ex.getCode());
//        System.out.println(ex.getErrMsg());
    }
}
