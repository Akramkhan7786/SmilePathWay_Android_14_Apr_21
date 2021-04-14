package app.smile.smilepathway.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {
    /**
     * status : true
     * error : {}
     * result : {"is_multi_practice":0,"is_password_generated":"1","userdata":{"username":"101773","id":"162","firstname":"Mike","lastname":"Parker","timestamp":1610526398,"email":"yashkhantal1212@gmail.com","mobile_number":"","gender":"M","dob":"Dec 12, 1940","age":80,"profile_image":""},"otp":{"2FA":"false","otp_code":"","otp_expiration_time":""},"practice":[{"practice_id":"10","name":"Ujjaval Vaishnav"}],"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IjEwMTc3MyIsImlkIjoiMTYyIiwiZmlyc3RuYW1lIjoiTWlrZSIsImxhc3RuYW1lIjoiUGFya2VyIiwidGltZXN0YW1wIjoxNjEwNTI2Mzk4fQ.uY41JblooLecj05DU4IqUa-dCSu-4pdH_gxlJh3SLdE","chat_details":{"chat_room":"SM_10_162_101773","chat_key":"d100f405a66ca78512431db75ea2f391.193f2ae57bd13421587ac66a504f001d.193f2ae57bd13421587ac66a504f001d","name":"Mike Parker","user_type":"patient"}}
     * code : 200
     */

    private boolean status;
    private ErrorBean error;
    private ResultBean result;
    private int code;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ErrorBean {
    }

    public static class ResultBean {
        /**
         * is_multi_practice : 0
         * is_password_generated : 1
         * userdata : {"username":"101773","id":"162","firstname":"Mike","lastname":"Parker","timestamp":1610526398,"email":"yashkhantal1212@gmail.com","mobile_number":"","gender":"M","dob":"Dec 12, 1940","age":80,"profile_image":""}
         * otp : {"2FA":"false","otp_code":"","otp_expiration_time":""}
         * practice : [{"practice_id":"10","name":"Ujjaval Vaishnav"}]
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IjEwMTc3MyIsImlkIjoiMTYyIiwiZmlyc3RuYW1lIjoiTWlrZSIsImxhc3RuYW1lIjoiUGFya2VyIiwidGltZXN0YW1wIjoxNjEwNTI2Mzk4fQ.uY41JblooLecj05DU4IqUa-dCSu-4pdH_gxlJh3SLdE
         * chat_details : {"chat_room":"SM_10_162_101773","chat_key":"d100f405a66ca78512431db75ea2f391.193f2ae57bd13421587ac66a504f001d.193f2ae57bd13421587ac66a504f001d","name":"Mike Parker","user_type":"patient"}
         */

        private String is_multi_practice;
        private String is_password_generated;
        private UserDataModel userdata;
        private OtpBean otp;
        private String token;
        private ChatDetailsModel chat_details;
        private List<PracticeBean> practice;

        public String getIs_multi_practice() {
            return is_multi_practice;
        }

        public void setIs_multi_practice(String is_multi_practice) {
            this.is_multi_practice = is_multi_practice;
        }

        public String getIs_password_generated() {
            return is_password_generated;
        }

        public void setIs_password_generated(String is_password_generated) {
            this.is_password_generated = is_password_generated;
        }

        public UserDataModel getUserdata() {
            return userdata;
        }

        public void setUserdata(UserDataModel userdata) {
            this.userdata = userdata;
        }

        public OtpBean getOtp() {
            return otp;
        }

        public void setOtp(OtpBean otp) {
            this.otp = otp;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public ChatDetailsModel getChat_details() {
            return chat_details;
        }

        public void setChat_details(ChatDetailsModel chat_details) {
            this.chat_details = chat_details;
        }

        public List<PracticeBean> getPractice() {
            return practice;
        }

        public void setPractice(List<PracticeBean> practice) {
            this.practice = practice;
        }

        public static class OtpBean {
            /**
             * 2FA : false
             * otp_code :
             * otp_expiration_time :
             */

            @SerializedName("2FA")
            private String _$2FA;
            private String otp_code;
            private String otp_expiration_time;

            public String get_$2FA() {
                return _$2FA;
            }

            public void set_$2FA(String _$2FA) {
                this._$2FA = _$2FA;
            }

            public String getOtp_code() {
                return otp_code;
            }

            public void setOtp_code(String otp_code) {
                this.otp_code = otp_code;
            }

            public String getOtp_expiration_time() {
                return otp_expiration_time;
            }

            public void setOtp_expiration_time(String otp_expiration_time) {
                this.otp_expiration_time = otp_expiration_time;
            }
        }

        public static class PracticeBean {
            /**
             * practice_id : 10
             * name : Ujjaval Vaishnav
             */

            private String practice_id;
            private String name;

            public String getPractice_id() {
                return practice_id;
            }

            public void setPractice_id(String practice_id) {
                this.practice_id = practice_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }



    /*
    *//**
     * status : true
     * error : {}
     * result : {"is_multi_practice":0,"is_password_generated":"1","userdata":{"username":"84308","id":"105","firstname":"Yashkhantal","lastname":"Hoppe","timestamp":1607407243,"email":"rekha.khantal13@gmail.com","mobile_number":"(536)624-5871","gender":"O","dob":"1988-12-10","age":31,"profile_image":"http://18.191.232.199/smilepathway/sm-patient-portal/assets/"},"otp":{"2FA":"true","otp_code":963470,"otp_expiration_time":1607407841},"practice":[{"practice_id":"8","name":"Ujjaval Vaishnav"}],"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Ijg0MzA4IiwiaWQiOiIxMDUiLCJmaXJzdG5hbWUiOiJZYXNoa2hhbnRhbCIsImxhc3RuYW1lIjoiSG9wcGUiLCJ0aW1lc3RhbXAiOjE2MDc0MDcyNDN9.AfeL-Vk_ZnZ0scP0OgEes6P9yJIXlKGjoUYBZ1fWFJ4"}
     * code : 200
     *//*

    private boolean status;
    private ErrorBean error;
    private ResultBean result;
    private int code;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ErrorBean {
    }

    public static class ResultBean {
        *//**
         * is_multi_practice : 0
         * is_password_generated : 1
         * userdata : {"username":"84308","id":"105","firstname":"Yashkhantal","lastname":"Hoppe","timestamp":1607407243,"email":"rekha.khantal13@gmail.com","mobile_number":"(536)624-5871","gender":"O","dob":"1988-12-10","age":31,"profile_image":"http://18.191.232.199/smilepathway/sm-patient-portal/assets/"}
         * otp : {"2FA":"true","otp_code":963470,"otp_expiration_time":1607407841}
         * practice : [{"practice_id":"8","name":"Ujjaval Vaishnav"}]
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Ijg0MzA4IiwiaWQiOiIxMDUiLCJmaXJzdG5hbWUiOiJZYXNoa2hhbnRhbCIsImxhc3RuYW1lIjoiSG9wcGUiLCJ0aW1lc3RhbXAiOjE2MDc0MDcyNDN9.AfeL-Vk_ZnZ0scP0OgEes6P9yJIXlKGjoUYBZ1fWFJ4
         *//*

        private String is_multi_practice;
        private String is_password_generated;
        private UserDataModel userdata;
        private OtpBean otp;
        private String token;
        private List<PracticeBean> practice;

        public String getIs_multi_practice() {
            return is_multi_practice;
        }

        public void setIs_multi_practice(String is_multi_practice) {
            this.is_multi_practice = is_multi_practice;
        }

        public String getIs_password_generated() {
            return is_password_generated;
        }

        public void setIs_password_generated(String is_password_generated) {
            this.is_password_generated = is_password_generated;
        }

        public UserDataModel getUserdata() {
            return userdata;
        }

        public void setUserdata(UserDataModel userdata) {
            this.userdata = userdata;
        }

        public OtpBean getOtp() {
            return otp;
        }

        public void setOtp(OtpBean otp) {
            this.otp = otp;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<PracticeBean> getPractice() {
            return practice;
        }

        public void setPractice(List<PracticeBean> practice) {
            this.practice = practice;
        }

        public static class OtpBean {
            *//**
             * 2FA : true
             * otp_code : 963470
             * otp_expiration_time : 1607407841
             *//*

            @SerializedName("2FA")
            private String _$2FA;
            private String otp_code;
            private String otp_expiration_time;

            public String get_$2FA() {
                return _$2FA;
            }

            public void set_$2FA(String _$2FA) {
                this._$2FA = _$2FA;
            }

            public String getOtp_code() {
                return otp_code;
            }

            public void setOtp_code(String otp_code) {
                this.otp_code = otp_code;
            }

            public String getOtp_expiration_time() {
                return otp_expiration_time;
            }

            public void setOtp_expiration_time(String otp_expiration_time) {
                this.otp_expiration_time = otp_expiration_time;
            }
        }

        public static class PracticeBean {
            *//**
             * practice_id : 8
             * name : Ujjaval Vaishnav
             *//*

            private String practice_id;
            private String name;

            public String getPractice_id() {
                return practice_id;
            }

            public void setPractice_id(String practice_id) {
                this.practice_id = practice_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }*/
}
