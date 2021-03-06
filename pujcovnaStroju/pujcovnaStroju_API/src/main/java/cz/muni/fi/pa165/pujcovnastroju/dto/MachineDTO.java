package cz.muni.fi.pa165.pujcovnastroju.dto;

import java.util.List;

/**
 * Default implementation of Machine DTO
 * 
 * @author Michal Merta 374015
 */
public class MachineDTO {

	private Long id;
	private String label;
	private String description;
	private MachineTypeEnumDTO type;
	private List<LoanDTO> loans;
	private List<RevisionDTO> revisions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MachineTypeEnumDTO getType() {
		return type;
	}

	public void setType(MachineTypeEnumDTO type) {
		this.type = type;
	}

	public List<LoanDTO> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanDTO> loans) {
		this.loans = loans;
	}

	public List<RevisionDTO> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<RevisionDTO> revisions) {
		this.revisions = revisions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((loans == null) ? 0 : loans.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineDTO other = (MachineDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (loans == null) {
			if (other.loans != null)
				return false;
		} else if (!loans.equals(other.loans))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MachineDTO [id=" + id + ", label=" + label + ", decription="
				+ description + ", type=" + type + ", loans=" + loans + "]";
	}

}
