package app.smile.smilepathway.model;

public class ErrorModel {

    /**
     * error : {"error":400,"errormsg":"Either username or password isn't valid"}
     */
    private int error;
    private String errormsg;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

   /* private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        *//**
         * error : 400
         * errormsg : Either username or password isn't valid
         *//*

        private int error;
        private String errormsg;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getErrormsg() {
            return errormsg;
        }

        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }
    }*/
}
