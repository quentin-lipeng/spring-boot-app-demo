package org.quentin.springbootredisapp.dao;

import org.quentin.springbootredisapp.dto.CommonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CommonOrder, Long> {

}
