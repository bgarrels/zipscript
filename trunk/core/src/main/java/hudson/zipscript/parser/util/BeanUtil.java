package hudson.zipscript.parser.util;

import java.lang.reflect.Method;

public class BeanUtil {

    public static Method getPropertyMethod (Object bean, String name, Object[] parameters) {
        if (bean == null) {
            return null;
        }
        if (name == null) {
            throw new IllegalArgumentException("No name specified for bean class '" +
                    bean.getClass() + "'");
        }

        Class clazz = bean.getClass();
        if (null == parameters || parameters.length == 0) {
        	// simple getter
        	Method m = null;
        	try {
        		m = clazz.getMethod("get" + StringUtil.firstLetterUpperCase(name), new Class[0]);
        	}
        	catch (NoSuchMethodException e1) {
        		try {
        			m = clazz.getMethod("get" + name, new Class[0]);
        		}
        		catch (NoSuchMethodException e2) {
        			try {
        				m = clazz.getMethod(StringUtil.firstLetterLowerCase(name), new Class[0]);
        			}
        			catch (NoSuchMethodException e3) {
        				return null;
        			}
        		}
        	}
        	return m;
        }
        else {
        	Class[] parameterTypes = new Class[parameters.length];
        	for (int i=0; i<parameters.length; i++)
        		parameterTypes[i] = (null != parameters[i])? parameters[i].getClass() : Object.class;
        	// we have to find a real method match
        	try {
        		return clazz.getMethod(name, parameterTypes);
        	}
        	catch (NoSuchMethodException e) {
        		return null;
        	}
        }
    }
}