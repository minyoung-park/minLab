package miny.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import miny.common.exception.MyException;

public class ActionResultForMessage {
  private String message;
  private String image_url;
  private String link_url;
  private List<Choice> choices;
  private List<Template> templates;
  private String add_data;
  private MyException.MyError myError;
  private Map<String, String> slots;
  
  public Map<String, String> getSlots() {
    return slots;
  }
  public void setSlots(Map<String, String> slots) {
    this.slots = slots;
  }
  public ActionResultForMessage() {
    
  }
  
  public MyException.MyError getMyError() {
    return myError;
  }
  
  public void setMyError(MyException.MyError myError) {
    this.myError = myError;
  }
  
  public void setAdd_data_from_object(Object obj) {
    if (obj != null) {
      Gson gson = new Gson();
      setAdd_data(gson.toJson(obj));
    }
  }
  
  public String getAdd_data() {
    return add_data;
  }
  
  public void setAdd_data(String add_data) {
    this.add_data = add_data;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public String getImage_url() {
    return image_url;
  }
  
  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }
  
  public String getLink_url() {
    return link_url;
  }
  
  public void setLink_url(String link_url) {
    this.link_url = link_url;
  }
  
  public List<Choice> getChoices() {
    return choices;
  }
  
  public void setChoices(List<Choice> choices) {
    this.choices = choices;
  }
  
  public List<Template> getTemplates() {
    return templates;
  }
  
  public void setTemplates(List<Template> templates) {
    this.templates = templates;
  }
  
  public void addChoices(Choice vo) {
    if (choices == null) choices = new ArrayList();
    choices.add(vo);
  }
  
  public void addTemplate(Template vo) {
    if (templates == null) templates = new ArrayList();
    templates.add(vo);
  }
}
