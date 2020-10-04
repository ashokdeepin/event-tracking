package com.ashok.eventtracking.repository;

import com.ashok.eventtracking.model.RedisEventLastAccess;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ashok
 * 03/10/20
 */
public interface RedisEventLastAccessRepository extends CrudRepository<RedisEventLastAccess, String> {
}
