package app.smile.smilepathway.model;

public class ForgotModel {

    /**
     * status : true
     * error : {}
     * result : {"username":"126311","otp":{"otp_code":894499,"otp_expiration_time":1605263546}}
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
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * username : 126311
         * otp : {"otp_code":894499,"otp_expiration_time":1605263546}
         */

        private String id;
        private String username;
        private OtpBean otp;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public OtpBean getOtp() {
            return otp;
        }

        public void setOtp(OtpBean otp) {
            this.otp = otp;
        }

        public static class OtpBean {
            /**
             * otp_code : 894499
             * otp_expiration_time : 1605263546
             */

            private String otp_code;
            private String otp_expiration_time;

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
    }
}
