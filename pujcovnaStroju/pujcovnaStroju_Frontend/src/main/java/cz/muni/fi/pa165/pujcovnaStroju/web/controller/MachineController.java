package cz.muni.fi.pa165.pujcovnaStroju.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.pa165.pujcovnastroju.dto.LoanDTO;
import cz.muni.fi.pa165.pujcovnastroju.dto.MachineDTO;
import cz.muni.fi.pa165.pujcovnastroju.dto.SystemUserDTO;
import cz.muni.fi.pa165.pujcovnastroju.entity.MachineTypeEnum;
import cz.muni.fi.pa165.pujcovnastroju.entity.UserTypeEnum;
import cz.muni.fi.pa165.pujcovnastroju.service.LoanService;
import cz.muni.fi.pa165.pujcovnastroju.service.MachineService;
import cz.muni.fi.pa165.pujcovnastroju.service.RevisionService;

/**
 * Machine controller implementation
 * 
 * @author Michal Merta
 * 
 */
@Controller
@RequestMapping("/machine")
public class MachineController {

	MachineService machineService;
	LoanService loanService;
	RevisionService revisionService;

	@Autowired
	public MachineController(MachineService machineService,
			RevisionService revisionService, LoanService loanService) {
		this.machineService = machineService;
		this.loanService = loanService;
		this.revisionService = revisionService;
	}

	@RequestMapping("")
	public String redirectToList(ModelMap model) {
		return "redirect:/machine/list";
	}

	@RequestMapping("/")
	public String redirectToListBackslash(ModelMap model) {
		return "redirect:/machine/list";
	}

	@RequestMapping(value = "/list"
	// , params = {"stored","errorText"}, method = RequestMethod.GET
	)
	public ModelAndView listMachines(
			ModelMap model,
			@RequestParam(value = "storeStatus", required = false, defaultValue = "") String storeStatus,
			@RequestParam(value = "deleteStatus", required = false, defaultValue = "") String deleteStatus,
			@RequestParam(value = "updateStatus", required = false, defaultValue = "") String updateStatus,
			@RequestParam(value = "errorMessage", required = false, defaultValue = "") String errorMessage) {

		model.addAttribute("machines", machineService.getAllMachines());
		model.addAttribute("list", "list of machines");
		model.addAttribute("types", MachineTypeEnum.class.getEnumConstants());
		model.addAttribute("pageTitle", "lang.listMachinesTitle");
		DefaultController.addHeaderFooterInfo(model);
		if (storeStatus.equalsIgnoreCase("true")) {
			model.addAttribute("storeStatus", "true");
		}

		if (storeStatus.equalsIgnoreCase("false")) {
			model.addAttribute("storeStatus", "false");
			model.addAttribute("errorMessage", errorMessage);
		}

		if (deleteStatus.equalsIgnoreCase("true")) {
			model.addAttribute("deleteStatus", "true");
		}

		if (deleteStatus.equalsIgnoreCase("false")) {
			model.addAttribute("deleteStatus", "false");
			model.addAttribute("errorMessage", errorMessage);
		}
		
		if (updateStatus.equalsIgnoreCase("true")) {
			model.addAttribute("updateStatus", "true");
		}

		if (updateStatus.equalsIgnoreCase("false")) {
			model.addAttribute("updateStatus", "false");
			model.addAttribute("errorMessage", errorMessage);
		}

		return new ModelAndView("listMachines", "command", new MachineDTO());
	}

	@RequestMapping(value = "/listByParams", method = RequestMethod.GET)
	public ModelAndView listMachinesByParams(ModelMap model,
			@RequestParam(value = "from", required = false) Date from,
			@RequestParam(value = "till", required = false) Date till) {

		List<MachineDTO> machines = machineService.getMachineDTOsByParams(
				null, null, null, null, null, from, till);
	    
		model.addAttribute("machines", machines);

		return new ModelAndView("listMachinesByParams");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addMachine(@ModelAttribute("machine") MachineDTO machine,
			BindingResult result, ModelMap model) {
		boolean stored = false;
		String errorMsg = null;

		try {
			stored = machineService.create(machine) != null;
		} catch (DataAccessException e) {
			stored = false;
			errorMsg = e.getMessage();
		}

		model.addAttribute("storeStatus", stored);
		if (errorMsg != null) {
			model.addAttribute("errorMessage", errorMsg);
		}
		return "redirect:list";
	}

	@RequestMapping("/detail/{id}")
	public String viewMachine(@PathVariable String id, ModelMap model) {
		DefaultController.addHeaderFooterInfo(model);
		model.addAttribute("pageTitle", "lang.detailMachineTitle");
		MachineDTO machine = null;
		boolean found = false;
		try {
			Long machineID = Long.valueOf(id);
			machine = machineService.read(machineID);
			found = machine != null;
		} catch (DataAccessException | NumberFormatException e) {
			// TODO log
		}

		if (found) {
			model.addAttribute("machine", machine);
			List<LoanDTO> loans = new ArrayList<>();
			for (LoanDTO loan : machine.getLoans()) {
				loans.add(loanService.read(loan.getId()));
			}
			model.addAttribute("loans", loans.size() == 0 ? null : loans);
		} else {
			model.addAttribute("id", id);
		}

		return "machineDetail";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteMachine(@PathVariable String id, ModelMap model) {
		boolean deleted = false;
		String errorMsg = null;
		MachineDTO machine = new MachineDTO();
		try {
			Long machineID = Long.valueOf(id);
			machine = machineService.read(machineID);
			machineService.delete(machine);
			deleted = true;
		} catch (DataAccessException | NumberFormatException
				| NullPointerException e) {
			// TODO log
			deleted = false;
			errorMsg = e.getMessage();
		}

		model.addAttribute("deleteStatus", deleted);
		if (errorMsg != null) {
			model.addAttribute("errorMessage", errorMsg);
		}
		return "redirect:/machine/list";
	}

	@RequestMapping(value = "/update/{id}")
	public ModelAndView updateUser(@PathVariable String id, ModelMap model) {
		DefaultController.addHeaderFooterInfo(model);
		model.addAttribute("pageTitle", "lang.updateUserTitle");
		MachineDTO machine = null;
		boolean found = false;
		try {
			Long machineID = Long.valueOf(id);
			machine = machineService.read(machineID);
			found = true;
		} catch (DataAccessException | NumberFormatException e) {
			// TODO log
		}

		List<MachineTypeEnum> types = new ArrayList<>();
		for (MachineTypeEnum e : MachineTypeEnum.class.getEnumConstants()) {
			if (!e.toString().equals(machine.getType().getTypeLabel())) {
				types.add(e);
			}
		}

		model.addAttribute("types", types);
		model.addAttribute("machine", machine);
		if (!found) {
			model.addAttribute("id", id);
		}
		return new ModelAndView("updateMachine", "command", new MachineDTO());
	}

	@RequestMapping(value = "/update/update", method = RequestMethod.POST)
	public String editUser(@ModelAttribute("machine") MachineDTO machine,
			BindingResult result, ModelMap model) {
		boolean updated = false;
		String errorMsg = null;
		try {
			updated = machineService.update(machine) != null;
		} catch (DataAccessException e) {
			updated = false;
			errorMsg = e.getMessage();
		}
		model.addAttribute("updateStatus", updated);
		if (errorMsg != null) {
			model.addAttribute("errorMessage", errorMsg);
		}
		return "redirect:/machine/list";
	}

}
