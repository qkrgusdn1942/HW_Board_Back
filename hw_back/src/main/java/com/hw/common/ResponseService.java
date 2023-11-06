package com.hw.common;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ResponseService {

	public <T> DataResponse<T> getSingleDataResponse(boolean success, String message, T data) {
		DataResponse<T> response = new DataResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);

        return response;
    }
	
	public <T> DataResponse<T> getListDataResponse(boolean success, String message, List<T> dataList) {
		DataResponse<T> response = new DataResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setDataList(dataList);

        return response;
    }
	
	public BaseResponse getBaseResponse(boolean success, String message) {
		BaseResponse response = new BaseResponse();
        response.setSuccess(success);
        response.setMessage(message);

        return response;
    }
}
