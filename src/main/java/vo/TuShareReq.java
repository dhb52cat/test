package vo;

import java.util.HashMap;

public class TuShareReq {
        private String api_name;
        private String token;
        private HashMap params;
        private String fields;
        public void setApi_name(String api_name) {
            this.api_name = api_name;
        }
        public String getApi_name() {
            return api_name;
        }

        public void setToken(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }

        public void setParams(HashMap params) {
            this.params = params;
        }
        public HashMap getParams() {
            return params;
        }

        public void setFields(String fields) {
            this.fields = fields;
        }
        public String getFields() {
            return fields;
        }

    }
