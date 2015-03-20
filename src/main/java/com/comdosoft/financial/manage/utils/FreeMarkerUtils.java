package com.comdosoft.financial.manage.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;

/**
 * Created by quqiang on 15/3/19.
 */
public class FreeMarkerUtils {

    public static TemplateModel useClass(String className) throws TemplateModelException
    {
        Configuration cfg = new Configuration();
        BeansWrapper wrapper = (BeansWrapper) cfg.getObjectWrapper();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        return staticModels.get(className);
    }
    //拿到目标对象的model
    public static TemplateModel useObjectModel(Object target) throws TemplateModelException
    {
        Configuration cfg = new Configuration();
        ObjectWrapper wrapper = (BeansWrapper) cfg.getObjectWrapper();
        TemplateModel model = wrapper.wrap(target);
        return model;
    }

    //拿到目标对象某个方法的Model
    public static TemplateModel useObjectMethod(Object target, String methodName) throws TemplateModelException
    {
        TemplateHashModel model = (TemplateHashModel) useObjectModel(target);
        return model.get(methodName);
    }
}
