/*
 * Copyright (c) 2015-2016, AlexGao
 * http://git.oschina.net/wolfsmoke/WSHttpHelper
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuanwu.ump.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class XmlUtil {
    public static Map<String,Object> xmlToMap(File xmlFile) throws Exception{
        XmlParser parser = new XmlParser();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            parser.parser(map, FileUtils.readFileToByteArray(xmlFile),null);
        } catch (IOException e) {
            throw new Exception(e.getMessage(),e);
        }
        return map;
    }

    public static Map<String,Object> xmlToMap(String xml)throws Exception{
        XmlParser parser = new XmlParser();
        Map<String,Object> map = new HashMap<String, Object>();
        parser.parser(map,xml.getBytes(),null);
        return map;
    }
}
