package vo;


import java.util.List;

public   class Data {
    private List<String> fields;
    private List<List<String>> items;
    public void setFields(List<String> fields) {
        this.fields = fields;
    }
    public List<String> getFields() {
        return fields;
    }

    public void setItems(List<List<String>> items) {
        this.items = items;
    }
    public List<List<String>> getItems() {
        return items;
    }
}
