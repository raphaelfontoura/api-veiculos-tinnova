package com.tinnova.vehicles_system.repositories;

import com.tinnova.vehicles_system.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.vendido=false")
    Integer countNotSell();

    @Query(value = "select \n" +
            "  case \n" +
            "    when ano between 1970 and 1979 then '1970-1979'\n" +
            "    when ano between 1980 and 1989 then '1980-1989'\n" +
            "    when ano between 1990 and 1999 then '1990-1999'\n" +
            "    when ano between 2000 and 2009 then '2000-2009'\n" +
            "    when ano between 2010 and 2019 then '2010-2019'\n" +
            "    when ano between 2020 and 2029 then '2020-2029'\n" +
            "    else 'OTHERS'\n" +
            "  end as `Decade`,\n" +
            "  count(1) as `Count`\n" +
            "from tb_veiculos \n" +
            "group by `Decade`", nativeQuery = true)
    List<String> countDecada();

    @Query("select v.marca, count(1) from Vehicle v GROUP BY v.marca")
    List<String> countFabricante();
}
