package com.aurora.util;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @说明:Objet 工具类
 */
public class BasicObjectUtils extends  cn.hutool.core.util.ObjectUtil{

	private static Logger log = LoggerFactory.getLogger(BasicObjectUtils.class);


	/**
	  * @说明: 字符串、集合、map、数组: 为空或者 empty
	  * @param
	  * @return
	  */
	public static boolean isNullOrEmpty(Object obj) {

		if(isEmpty(obj)) {
			return true;
		}


		return false;
	}

	public static boolean isNotNullOrEmpty(Object obj) {  
		
		return !isNullOrEmpty(obj);
		
	}
	
	/**
	 * @Title: copyProperties
	 * @Description: 对象属性拷贝(将源对象的属性值拷贝到目标对象)
	 * @param @param source 源对象
	 * @param @param target 目标对象
	 */
	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}

	/**
	 * @Description : 根据属性名称获取属性值
	 * @param : object		对象
	 * @param : clazz		类
	 * @param : fieldName	属性名
	 * @return : Object
	 */
	public static Object getFieldValue(Object object,Class<?> clazz,String fieldName) {
		Object val = null;
		if(clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				val = field.get(object);
			} catch (NoSuchFieldException e) {
				return getFieldValue(object, clazz.getSuperclass(), fieldName);
			} catch (Exception e) {
				log.error("获取属性值系统异常",e);
			}
		}
		return val;
	}

	public static void setFieldValue(Object obj,String tribute,Object value){
		if(isEmpty(value)){
			return ;
		}
		if (obj instanceof java.util.HashMap) {
			Map Map = (java.util.Map) obj;
			Map.put(tribute,value);
		}else{
			Class<? extends Object> class1 = obj.getClass();
			try {
				Field field = class1.getDeclaredField(tribute);
				field.setAccessible(true);
				field.set(obj, value);
			} catch (NoSuchFieldException e) {
				setFieldValue(obj, class1.getSuperclass(), tribute,value);
			} catch (Exception e){
				log.error("设置属性值系统异常",e);
			}
		}
	}

	/**
	 * @Description : 根据属性名称设置属性值
	 * @param : object		对象
	 * @param : clazz		类
	 * @param : fieldName	属性名
	 * @param : filedValue	属性值
	 * @return : void
	 */
	public static void setFieldValue(Object object,Class<?> clazz,String fieldName,Object filedValue) {
		if(clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, filedValue);
			} catch (NoSuchFieldException e) {
				setFieldValue(object, clazz.getSuperclass(), fieldName,filedValue);
			} catch (Exception e) {
				log.error("设置属性值系统异常",e);
			}
		}
	}

	/**
	 * 开始分页
	 * @param list
	 * @param pageNum 页码
	 * @param pageSize 每页多少条数据
	 * @return
	 */
	public static List startPage(List list, Integer pageNum,
								 Integer pageSize) {
		if (list.size() == 0) {
			return list;
		}

		Integer count = list.size(); // 记录总数
		Integer pageCount = 0; // 页数
		if (count % pageSize == 0) {
			pageCount = count / pageSize;
		} else {
			pageCount = count / pageSize + 1;
		}

		int fromIndex = 0; // 开始索引
		int toIndex = 0; // 结束索引

		if (pageNum > pageCount) {
			list.clear();
			return list;
		}

		if (!pageNum.equals(pageCount)) {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = fromIndex + pageSize;
		} else {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = count;
		}

		List pageList = list.subList(fromIndex, toIndex);

		return pageList;
	}

	/**
	 * 深层复制工具
	 * @param  obj
	 * @return T
	 */
	public static <T extends Serializable> T deepCopy(T obj) {
		T target = SerializationUtils.clone(obj);
		return target;
	}

	/**
	 * 根据源集合复制一份新集合返回，转换对象类型
	 * @param copyList 源集合
	 * @param clazz 需要复制的对象类型
	 * @param <T> 数据泛型
	 * @return List<T>
	 */
	public static <T> List<T> listCopyProperties(List copyList,Class<T> clazz) {
		List<T> sourceList = new ArrayList<>();
		if(BasicObjectUtils.isNotEmpty(copyList)){
			copyList.forEach(item -> {
				T vo = null;
				try {
					vo = (T) clazz.newInstance();
				} catch (InstantiationException e) {
					log.error("listCopyProperties--系统异常",e);
				} catch (IllegalAccessException e) {
					log.error("listCopyProperties--系统异常",e);
				}
				BasicObjectUtils.copyProperties(item,vo);
				sourceList.add(vo);
			});
		}
		return sourceList;
	}


	public static <T> List<T> deepCopy(List<T> src) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(src);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			List<T> dest = (List<T>) in.readObject();
			return dest;
		}catch (Exception e){
			log.error("deepCopy--系统异常",e);
		}
		return  null ;
	}
}
