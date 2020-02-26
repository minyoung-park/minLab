package miny.vo;

import java.util.Map;

public class Choice {
  
  private Integer answer_no;
  private String answer_script;
  private String verifying_words;
  private String answerValue;
  private Map data;
  
  public Choice(Integer answer_no, String answer_script, String answerValue, String verifying_words, Map data) {
    this.answer_no = answer_no;
    this.answer_script = answer_script;
    this.verifying_words = verifying_words;
    this.answerValue = answerValue;
    this.data = data;
  }
  
  public Choice(Integer answer_no, String answer_script, String answerValue) {
    this.answer_no = answer_no;
    this.answer_script = answer_script;
    this.answerValue = answerValue;
  }

  public Choice(Integer answer_no, String answer_script) {
    this.answer_no = answer_no;
    this.answer_script = answer_script;
  }
   
  public Integer getAnswer_no() {
    return answer_no;
  }
  
  public void setAnswer_no(Integer answer_no) {
    this.answer_no = answer_no;
  }
  
  public String getAnswer_script() {
    return answer_script;
  }
  
  public void setAnswer_script(String answer_script) {
    this.answer_script = answer_script;
  }
  
  public String getVerifying_words() {
    return verifying_words;
  }
  
  public void setVerifying_words(String verifying_words) {
    this.verifying_words = verifying_words;
  }
  
  public String getAnswerValue() {
    String result = answerValue;
    if (answerValue == null || answerValue.equals("")) result = getAnswer_script();
    return result;
  }
  
  public void setAnswerValue(String answerValue) {
    this.answerValue = answerValue;
  }
  
  public Map getData() {
    return data;
  }
  
  public void setData(Map data) {
    this.data = data;
  }
}
