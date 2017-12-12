package com.sdzx.xtbg.bean;

public class UpdateInfo {

    /**
     * VersionValue : {"VersionOld":"1","ApkUrl":"http://mlxc.sdzxkj.cn/mlxc.apk"}
     */

    private VersionValueBean VersionValue;

    public VersionValueBean getVersionValue() {
        return VersionValue;
    }

    public void setVersionValue(VersionValueBean VersionValue) {
        this.VersionValue = VersionValue;
    }

    public static class VersionValueBean {
        /**
         * VersionOld : 1
         * ApkUrl : http://mlxc.sdzxkj.cn/mlxc.apk
         */

        private String VersionOld;
        private String ApkUrl;

        public String getVersionOld() {
            return VersionOld;
        }

        public void setVersionOld(String VersionOld) {
            this.VersionOld = VersionOld;
        }

        public String getApkUrl() {
            return ApkUrl;
        }

        public void setApkUrl(String ApkUrl) {
            this.ApkUrl = ApkUrl;
        }
    }
}
