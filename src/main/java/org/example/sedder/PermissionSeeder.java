package org.example.sedder;

import lombok.RequiredArgsConstructor;
import org.example.entity.Permission;
import org.example.repository.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(PermissionSeeder.class);

    private final PermissionRepository permissionRepository;


    @Override
    public void run(String... args) {

        try {

            List<String> defaultPermissions = List.of(
                    "USER_READ",
                    "USER_CREATE",
                    "USER_UPDATE",
                    "USER_DELETE",
                    "ROLE_MANAGE"
            );


            for (String permName : defaultPermissions) {

                if (permissionRepository.findByName(permName).isEmpty()) {

                    Permission permission = new Permission();
                    permission.setName(permName);

                    permissionRepository.save(permission);

                    log.info("Seeded permission: {}", permName);
                }
            }


            log.info("Permission seeding completed.");

        } catch (Exception e) {

            log.error("Error while seeding permissions", e);
        }
    }
}