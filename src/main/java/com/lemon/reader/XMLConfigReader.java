package com.lemon.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lemon.config.Configuration;
import com.lemon.session.SqlMapper;

public class XMLConfigReader {
	
	private static final String DEFAULT_CONFIG_FILE="simulation_mybatis_config.xml";
	
	static ClassLoader getClassLoader(){
		return Thread.currentThread().getClass().getClassLoader();
	}
	
	static{
		// 读取配置文件
		DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
		InputStream input;
		try {
			input=getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
			DocumentBuilder docBuilder=docBuildFactory.newDocumentBuilder();
			Document document=docBuilder.parse(input);
			Element rootElement=document.getDocumentElement();
			NodeList dataSource= rootElement.getElementsByTagName("dataSource");
			if(dataSource==null){
				//throw 
			}
			NodeList properties=dataSource.item(0).getChildNodes();
			for(int i=0;i<properties.getLength();i++){
				Node node=properties.item(i);
				NamedNodeMap nnm= node.getAttributes();
				if(nnm==null){
					continue;
				}
				Configuration.getInstance().addConnectionInfo(nnm.getNamedItem("name").getNodeValue(), nnm.getNamedItem("value").getNodeValue());
			}
			NodeList mappers= rootElement.getElementsByTagName("mappers");
			for(int i=0;i<mappers.getLength();i++){
				NodeList currentNode=mappers.item(i).getChildNodes();
				for(int k=0;k<currentNode.getLength();k++){
					Node node=currentNode.item(k);
					NamedNodeMap nnm= node.getAttributes();
					if(nnm==null){
						continue;
					}
					String mapperResource =nnm.getNamedItem("resource").getNodeValue();
					readAllMapper(rootElement,mapperResource);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void readAllMapper(Element rootElement,String mapperResource)throws Exception{
		SqlMapper m=new SqlMapper();
		String fullPath=rootElement.getAttribute("interfaceFullPath");
		m.setInterfaceFullPath(fullPath);
		NodeList list=rootElement.getChildNodes();
		for(int i=0;i<list.getLength();i++){
			Node node=list.item(i);
			NamedNodeMap nnm= node.getAttributes();
			if(nnm==null){
				continue;
			}
			
			SqlMapper sqlMapper=new SqlMapper();
			sqlMapper.setId(nnm.getNamedItem("id").getNodeValue());
			if(nnm.getNamedItem("resultEntity")!=null){
				sqlMapper.setResultType(nnm.getNamedItem("resultEntity").getNodeValue());
			}
			if(nnm.getNamedItem("parameterType")!=null){
				sqlMapper.setParameterType(nnm.getNamedItem("parameterType").getNodeValue());
			}
			sqlMapper.setOperatorType(node.getNodeName());
			sqlMapper.setSql(node.getTextContent());
			sqlMapper.setInterfaceFullPath(fullPath);
			Configuration.getInstance().addMapper(fullPath+"."+nnm.getNamedItem("id").getNodeValue(), sqlMapper);
		}
	}
	
}
