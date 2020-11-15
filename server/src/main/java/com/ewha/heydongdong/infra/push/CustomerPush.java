package com.ewha.heydongdong.infra.push;

import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerPush {

    public static String buildCustomerPushOnProgressUpdate(String targetToken, Progress progress) throws JSONException {
        JSONObject data = buildData(progress);
        JSONObject body = buildBody(targetToken, data);
        return body.toString();
    }

    private static JSONObject buildData(Progress progress) {
        JSONObject data = new JSONObject();
        data.put("title", "주문 진행 알림");
        data.put("message", setMessage(progress));
        return data;
    }

    private static String setMessage(Progress progress) {
        switch (progress) {
            case MAKING:
                return "음료 제조가 시작되었습니다. 매장에 방문하여 대기해주세요.";
            case DECLINED:
                return "주문이 매장 사정에 의해 거절되었습니다. 매장에 직접 문의해주세요.";
            case READY:
                return "주문하신 음료가 모두 준비되었습니다. 매장에서 결제 후 수령해주세요.";
            case DONE:
                return "주문이 정상적으로 거래완료되었습니다. 헤이동동을 이용해주셔서 감사합니다.";
            case NOSHOW:
                return "주문이 노쇼 처리되었습니다. 누적 횟수에 따라 헤이동동 서비스 이용이 중지될 수 있습니다.";
            default:
                return null;
        }
    }

    private static JSONObject buildBody(String targetToken, JSONObject data) {
        JSONObject body = new JSONObject();
        body.put("to", targetToken);
        body.put("priority", "high");
        body.put("data", data);
        return body;
    }
}
