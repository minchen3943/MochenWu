import COS from "cos-js-sdk-v5";
import axios from "axios";
import config from "../../../mcw-config.json";

const cos = new COS({
  getAuthorization: function (_options, callback) {
    const stsUrl = `${config.server.axios.protocol}://${config.server.axios.host}/api/cos/token/get`;

    axios
      .get(stsUrl)
      .then((response) => {
        const data = response.data;
        if (!data?.credentials) {
          console.error("无效的凭证数据:", JSON.stringify(data, null, 2));
          return;
        }

        const credentials = data.credentials;
        callback({
          TmpSecretId: credentials.tmpSecretId,
          TmpSecretKey: credentials.tmpSecretKey,
          SecurityToken: credentials.token,
          ExpiredTime: data.expiredTime,
          StartTime: data.startTime,
          ScopeLimit: true,
        });
      })
      .catch((error) => {
        console.error("获取临时密钥失败:", error);
        throw new Error(error.message);
      });
  },
});
export default cos;
