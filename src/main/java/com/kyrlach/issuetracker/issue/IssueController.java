package com.kyrlach.issuetracker.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kyrlach.issuetracker.login.User;
import com.kyrlach.issuetracker.login.UserRepository;

@Controller
@RequestMapping("/issues")
public class IssueController {
	
	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.GET)
    public String issues(Model model) {
    	model.addAttribute("issues", issueRepository.findAll());
    	return "issueList";
    }
	
	@RequestMapping(value = "/new", method = RequestMethod.GET) 
	public String newIssue(Model model) {
		model.addAttribute("userList", userRepository.findAll());
		model.addAttribute("issueForm", new IssueForm());
		return "newIssue";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String saveIssue(@ModelAttribute IssueForm issueForm, Model model) {
		User assignee = userRepository.findOne(issueForm.getAssignedTo());
		Issue newIssue = new Issue(issueForm.getTitle(), issueForm.getDescription(), assignee);
		issueRepository.save(newIssue);
		return "redirect:/issues";
	}
}
