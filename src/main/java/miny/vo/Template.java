package miny.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template {
  
  private String style; // 추후 디자인 요소에 적용 예상에 따른 임의 값. 0: 기본
  private String text;  // Template 없는 플렛폼에서 표현할 텍스트
  private String image_url;
  private String image_action;
  private String title;
  private String title_align;
  private String contents;
  private String contents_align;
  private Map<String, Object> platform_data; // template에서 각 플렛폼(텔레그램,카카오등)에 따라 적용해야 하는 내용을 우선 맵으로 전달.
  private List<Button> buttons;
  
  public Template() {
    this.style = "0";
    this.title_align = "center";
    this.contents_align = "center";
  }
  
  public Map<String, Object> getPlatform_data() {
    return platform_data;
  }
  
  public void setPlatform_data(Map<String, Object> platform_data) {
    this.platform_data = platform_data;
  }
  
  public void addPlatform_data(String key, Object value) {
    if (platform_data == null) platform_data = new HashMap();
    platform_data.put(key, value);
  }
  
  public Object getPlatform_KeyData(String key) {
    if (platform_data != null && platform_data.containsKey(key)) return platform_data.get(key);
    return null;
  }
  
  public String getStyle() {
    return style;
  }
  
  public void setStyle(String style) {
    this.style = style;
  }
  
  public String getImage_action() {
    return image_action;
  }
  
  public void setImage_action(String image_action) {
    this.image_action = image_action;
  }
  
  public String getTitle_align() {
    return title_align;
  }
  
  public void setTitle_align(String title_align) {
    this.title_align = title_align;
  }
  
  public String getContents_align() {
    return contents_align;
  }
  
  public void setContents_align(String contents_align) {
    this.contents_align = contents_align;
  }
  
  public String getText() {
    return text;
  }
  
  public void setText(String text) {
    this.text = text;
  }
  
  public String getImage_url() {
    return image_url;
  }
  
  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContents() {
    return contents;
  }
  
  public void setContents(String contents) {
    this.contents = contents;
  }
  
  public List<Button> getButtons() {
    return buttons;
  }
  
  public void setButtons(List<Button> buttons) {
    this.buttons = buttons;
  }
  
  public void addButton(String button_type, String button_title, String button_payload) {
    if (buttons == null) buttons = new ArrayList();
    Button btn = new Button();
    if (button_type != null && !button_type.equals("")) btn.setButton_type(button_type);
    if (button_title != null && !button_title.equals("")) btn.setButton_title(button_title);
    if (button_payload != null && !button_payload.equals("")) btn.setButton_payload(button_payload);
    buttons.add(btn);
  }
  
  public class Button {
    // Button_type : CValue Check
    private String button_type;
    private String button_title;
    private String button_payload; // payload, url 등 type에 맞는 특성값
    /* private String button_payload_sub; // phone등 기능이 없는 애들을 위한 보조 */
    
    public String getButton_type() {
      return button_type;
    }
    
    public void setButton_type(String button_type) {
      this.button_type = button_type;
    }
    
    public String getButton_title() {
      return button_title;
    }
    
    public void setButton_title(String button_title) {
      this.button_title = button_title;
    }
    
    public String getButton_payload() {
      return button_payload;
    }
    
    public void setButton_payload(String button_payload) {
      this.button_payload = button_payload;
    }

  }
  
}
