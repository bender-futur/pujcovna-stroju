/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pujcovnastroju.converter;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pa165.pujcovnastroju.dto.MachineDTO;
import cz.muni.fi.pa165.pujcovnastroju.entity.Machine;

/**
 * 
 * @author Michal Merta 374015
 */
public class MachineDTOConverter {

	public static Machine dtoToEntity(MachineDTO dto) {
		if (dto == null)
			return null;

		Machine entity = new Machine();
		entity.setId(dto.getId());
		entity.setType(MachineTypeDTOConverter.dtoToEntity(dto.getType()));
		entity.setLabel(dto.getLabel());
		entity.setDescription(dto.getDescription());
		return entity;
	}

	public static MachineDTO entityToDto(Machine entity) {
		if (entity == null)
			return null;

		MachineDTO dto = new MachineDTO();
		dto.setId(entity.getId());
		dto.setType(MachineTypeDTOConverter.entityToDto(entity.getType()));
		dto.setLabel(entity.getLabel());
		dto.setDescription(entity.getDescription());
		return dto;
	}

	public static List<MachineDTO> listToDto(List<Machine> list) {
		if (list == null)
			return null;

		List<MachineDTO> resultList = new ArrayList<>();
		for (Machine entity : list) {
			resultList.add(MachineDTOConverter.entityToDto(entity));
		}
		return resultList;
	}

	public static List<Machine> listToEntities(List<MachineDTO> listDTO) {
		if (listDTO == null)
			return null;

		List<Machine> resultList = new ArrayList<>();
		for (MachineDTO dto : listDTO) {
			resultList.add(MachineDTOConverter.dtoToEntity(dto));
		}
		return resultList;
	}
}
