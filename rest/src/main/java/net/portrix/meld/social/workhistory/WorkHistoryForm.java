package net.portrix.meld.social.workhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Patrick Bittner on 21/12/2016.
 */
public class WorkHistoryForm {

    private UUID id;

    private final List<CompanyForm> companies = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<CompanyForm> getCompanies() {
        return companies;
    }

    public void addCompany(CompanyForm companyForm) {
        companies.add(companyForm);
    }
}
