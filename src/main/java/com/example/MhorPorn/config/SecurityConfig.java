package com.example.MhorPorn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                // เปิดให้แอปมือถือยิง API เข้ามาดึงข้อมูลได้โดยไม่ต้องล็อกอินผ่านหน้าเว็บ
                .requestMatchers("/api/v1/**").permitAll() 
                
                .requestMatchers("/tv").permitAll()
                .requestMatchers("/ws-queue/**").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/doctor", "/examine/**").hasRole("DOCTOR")
                .requestMatchers("/", "/register", "/triage/**").hasRole("NURSE")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .permitAll()
                // ลอจิก AI: เมื่อล็อกอินสำเร็จ ให้อ่าน Role แล้วส่งไปหน้าเว็บที่ถูกต้องทันที
                .successHandler((request, response, authentication) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                    if (roles.contains("ROLE_DOCTOR")) {
                        response.sendRedirect("/doctor");
                    } else if (roles.contains("ROLE_ADMIN")) {
                        response.sendRedirect("/admin");
                    } else {
                        response.sendRedirect("/");
                    }
                })
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // ออกจากระบบแล้วเด้งกลับหน้า Login
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); 
            
        return http.build();
    }

    // จำลองฐานข้อมูลพนักงาน (In-Memory) เพื่อความรวดเร็วในการทดสอบ
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails nurse = User.withDefaultPasswordEncoder()
            .username("nurse")
            .password("1234")
            .roles("NURSE")
            .build();

        UserDetails doctor = User.withDefaultPasswordEncoder()
            .username("doctor")
            .password("1234")
            .roles("DOCTOR")
            .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin").password("1234").roles("ADMIN").build();
           return new InMemoryUserDetailsManager(nurse, doctor, admin); 
    }
}