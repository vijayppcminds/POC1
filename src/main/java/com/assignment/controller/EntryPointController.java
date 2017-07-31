package com.assignment.controller;

import com.assignment.dto.RequestBean;
import com.assignment.util.JsonParserUtil;
import com.assignment.util.MediaEnum;
import com.assignment.util.Utility;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/media")
public class EntryPointController {

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String getData(@RequestParam(name="filter", required = true) String filterType, @RequestParam(name = "level", required = true) String levelType, @ModelAttribute RequestBean requestBean){
		boolean validationResult = Utility.validateInputParameters(requestBean, MediaEnum.REQUESTBEAN);
		if(!validationResult)return "Something wrong with the request parameters";
		return JsonParserUtil.parseJSON(requestBean, levelType);
	}
}
