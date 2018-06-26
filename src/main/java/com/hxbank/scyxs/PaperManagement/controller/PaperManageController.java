package com.hxbank.scyxs.PaperManagement.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.hxbank.scyxs.PaperManagement.service.PaperLabelService;

@RestController
@CrossOrigin
public class PaperManageController {
	
	PaperLabelService pls =new PaperLabelService();

	@RequestMapping(value = "/papermanagement/labelservice/getLabel",method = RequestMethod.GET)
    public String getPaperLabel(@RequestParam(value = "paperName")String paperName) {
        
		String rootBuisnessNode = "会计业务";
		
		return pls.getLabel(rootBuisnessNode,paperName);
    }

	@RequestMapping(value = "/papermanagement/labelservice/createLabel",method = RequestMethod.POST)
    public void createPaperLabel(@RequestParam(value = "paperName")String paperName,@RequestParam(value = "businessNode")String businessNode) {
        
		
		pls.createPaperNodeAndRel(businessNode, paperName);
	
    }
	
	@RequestMapping(value = "/papermanagement/labelservice/getSameBusinessPaper",method = RequestMethod.GET)
    public void getSameBusinessPaper(@RequestParam(value = "paperName")String paperName,@RequestParam(value = "businessNode")String businessNode) {
        
		
		pls.createPaperNodeAndRel(businessNode, paperName);
	
    }
}
