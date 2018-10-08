package YTOP.MLO.API.util;

import java.io.Serializable;

public class ResultJsonBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private boolean success;
  private Object result;
  private String resultCode;
  private String resultDescription;
  
  public ResultJsonBean()
  {
    this.result = "";
    this.resultCode = "";
    this.resultDescription = "";
  }
  
  public ResultJsonBean(boolean success, Object result)
  {
    this.success = success;
    this.result = result;
    this.resultCode = "";
    this.resultDescription = "";
  }
  
  public ResultJsonBean(Object result)
  {
    this.result = result;
  }
  
  public ResultJsonBean(boolean success, String resultCode, String resultDescription)
  {
    this.success = success;
    this.result = "";
    this.resultCode = resultCode;
    this.resultDescription = resultDescription;
  }
  
  public ResultJsonBean(boolean success, Object result, String resultCode, String resultDescription)
  {
    this.success = success;
    this.result = result;
    this.resultCode = resultCode;
    this.resultDescription = resultDescription;
  }
  
  public ResultJsonBean(boolean success, Object result, String resultCode)
  {
    this.success = success;
    this.result = result;
    this.resultCode = resultCode;
  }
  
  public boolean isSuccess()
  {
    return this.success;
  }
  
  public void setSuccess(boolean success)
  {
    this.success = success;
  }
  
  public Object getResult()
  {
    return this.result;
  }
  
  public void setResult(Object result)
  {
    this.result = result;
  }

  public String getResultCode() {
	return resultCode;
  }

  public void setResultCode(String resultCode) {
	this.resultCode = resultCode;
  }

  public String getResultDescription() {
	return resultDescription;
  }

  public void setResultDescription(String resultDescription) {
	this.resultDescription = resultDescription;
  }

}
