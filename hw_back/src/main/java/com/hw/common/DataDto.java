package com.hw.common;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataDto<T> extends BaseDto{

	private T data; // 단일 데이터 전달
	private List<T> dataList; // 다중 데이터 전달 
}
