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
            for (int i=0; i<clazz.getMethods().length; i++) {
            	Method m = clazz.getMethods()[i];
            	if (m.getName().equals(name) && m.getParameterTypes().length == parameters.length) {
            		// probably a good fit
            		boolean isOk = true;
            		for (int j=0; j<parameters.length; j++) {
            			if (null != parameters[j])
            				isOk = isOk && doesParameterFit(parameters[j].getClass(), m.getParameterTypes()[j]);
            			if (!isOk) break;
            		}
            		if (isOk)
            			return m;
            	}
            }
            return null;
        }
    }

    private static boolean doesParameterFit (Class obj, Class type) {
    	if (null == obj) return true;
    	else if (obj.equals(type)) return true;
    	else {
    		// check super classes
    		Class parent = obj.getSuperclass();
    		if (null != parent) {
    			if (doesParameterFit(parent, type)) return true;
    		}
    		// check interfaces
    		for (int i=0; i<obj.getInterfaces().length; i++) {
    			if (doesParameterFit(obj.getInterfaces()[i], type)) return true;
    		}
    		return false;
    	}
    }
}