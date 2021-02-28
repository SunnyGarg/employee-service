package com.paypal.bfs.test.employeeserv.entity;

import com.paypal.bfs.test.employeeserv.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = Constants.ADDRESS_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity implements Serializable {

    //Keeping id as UUID, auto incremented value could be a risk when we migrate the data.
    @Id
    @Column(name = "address_id", nullable = false)
    private String addressId;

    @Column(name = "line1", length = 255, nullable = false)
    private String line1;

    @Column(name = "line2", length = 255, nullable = true)
    private String line2;

    @Column(name = "city", length = 255, nullable = false)
    private String city;

    @Column(name = "state", length = 255, nullable = false)
    private String state;

    @Column(name = "country", length = 127, nullable = false)
    private String country;

    @Column(name = "zip_code", length = 31, nullable = false)
    private String zipCode;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "addresses")
    private Set<EmployeeEntity> employees;
}
