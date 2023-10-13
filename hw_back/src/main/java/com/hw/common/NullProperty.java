package com.hw.common;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class NullProperty {
	
	public static void copyProperty (Object baseObj, Object targetObj) {
		BeanUtils.copyProperties(baseObj, targetObj, getNullPropertyNames(baseObj));
	}
	
	public static String[] getNullPropertyNames (Object baseObj) {
		final BeanWrapper src = new BeanWrapperImpl(baseObj);
	    PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}

}
