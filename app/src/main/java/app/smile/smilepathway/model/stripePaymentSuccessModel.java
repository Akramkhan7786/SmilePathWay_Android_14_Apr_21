package app.smile.smilepathway.model;

public class stripePaymentSuccessModel {

    /**
     * status : true
     * error : {}
     * result : {"message":"Payment Successfully Completed, With Smile Credit Points Inseterd"}
     * code : 200
     */

    private Boolean status;

    private ErrorDTO error;

    private ResultDTO result;

    private Integer code;

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public static class ErrorDTO {
    }

    public static class ResultDTO  {
        /**
         * message : Payment Successfully Completed, With Smile Credit Points Inseterd
         */

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
