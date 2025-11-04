package com.foliageh.itmosoalab2.api;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.InetAddress;
import java.util.UUID;

@Singleton
@Startup
public class ConsulRegistrationService {

    private String serviceId;
    private ConsulClient consulClient;

    @PostConstruct
    public void registerService() {
        try {
            consulClient = new ConsulClient("localhost", 8500);

            NewService newService = new NewService();
            serviceId = "flat-service-" + UUID.randomUUID().toString();
            newService.setId(serviceId);
            newService.setName("flat-service");
            newService.setAddress(InetAddress.getLocalHost().getHostAddress());
            newService.setPort(8182);

            // Health check с отключенной SSL верификацией
            NewService.Check check = new NewService.Check();
//            check.setHttp("https://localhost:8182/itmo-soa-lab2/api/flats/health");
//            check.setTlsSkipVerify(true); // ← ВАЖНО: отключаем SSL верификацию
//            check.setInterval("15s");     // Увеличим интервал для надежности
//            check.setTimeout("10s");      // Увеличим timeout
            check.setTcp("localhost:8182"); // Просто проверяет доступность порта
            check.setInterval("15s");
            check.setTimeout("10s");
            newService.setCheck(check);

            consulClient.agentServiceRegister(newService);
            System.out.println("Service registered in Consul with ID: " + serviceId);

        } catch (Exception e) {
            System.err.println("Failed to register service in Consul: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void deregisterService() {
        if (consulClient != null && serviceId != null) {
            consulClient.agentServiceDeregister(serviceId);
            System.out.println("✅ Service deregistered from Consul: " + serviceId);
        }
    }
}