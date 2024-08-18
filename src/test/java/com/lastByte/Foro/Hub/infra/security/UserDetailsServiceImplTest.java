package com.lastByte.Foro.Hub.infra.security;

import com.lastByte.Foro.Hub.domain.Permiso.Permiso;
import com.lastByte.Foro.Hub.domain.Permiso.PermisoEnum;
import com.lastByte.Foro.Hub.domain.Role.Role;
import com.lastByte.Foro.Hub.domain.Role.RoleEnum;
import com.lastByte.Foro.Hub.domain.Role.RoleRepository;
import com.lastByte.Foro.Hub.domain.Usuario.Usuario;
import com.lastByte.Foro.Hub.domain.Usuario.UsuarioRepository;
import com.lastByte.Foro.Hub.infra.exceptions.CollectionEmptyException;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthCreateRoleRequest;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthLoginRequest;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthRegisterUserRequest;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthResponse;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }












    // Test for loadUserByUsername
    @Test
    public void testLoadUserByUsername_UsuarioExiste() {
        String username = "testUser";
        Usuario usuario = new Usuario(); // Asume que Usuario implementa UserDetails

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(usuario, userDetails);
    }




    @Test
    public void testLoadUserByUsername_UsuarioNoExiste() {

        String username = "testUser";
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername(username);
        });

        assertEquals("El usuario " + username + " no existe", thrown.getMessage());
    }








    // Test for login
    @Test
    public void testLogin_CredencialesCorrectas() {
        String username = "testUser";
        String password = "testPass";
        AuthLoginRequest authLoginRequest = new AuthLoginRequest(username, password);

        Usuario usuario = new Usuario(); // Asume que Usuario implementa UserDetails
        usuario.setPassword("encodedPass");

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, "encodedPass")).thenReturn(true);

        AuthResponse authResponse = userDetailsServiceImpl.login(authLoginRequest);

        assertNotNull(authResponse);
        assertEquals(username, authResponse.username());
        assertEquals("Usuario logeado con exito!", authResponse.message());
        assertTrue(authResponse.status());
    }












    @Test
    public void testLogin_CredencialesIncorrectas() {
        String username = "testUser";
        String password = "testPass";
        AuthLoginRequest authLoginRequest = new AuthLoginRequest(username, password);

        Usuario usuario = new Usuario(); // Asume que Usuario implementa UserDetails

        usuario.setPassword("encodedPass");

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, "encodedPass")).thenReturn(false);

        BadCredentialsException thrown = assertThrows(BadCredentialsException.class, () -> {
            userDetailsServiceImpl.login(authLoginRequest);
        });

        assertEquals("Password invalido!", thrown.getMessage());
    }









    // Test for registerUser
    @Test
    public void testRegisterUser_UsuarioRegistradoExitosamente() {
        String  nombre ="user";
        String username = "testUser";
        String email = "testEmail@test.com";
        String password = "testPass";
        List<String> roles = List.of("USER");

        AuthRegisterUserRequest registerUserRequest = new AuthRegisterUserRequest(nombre,email,username,  password,  new AuthCreateRoleRequest(roles));
        Usuario usuario = new Usuario(); // Asume que Usuario implementa UserDetails

        when(usuarioRepository.existsByEmail(email)).thenReturn(false);
        when(usuarioRepository.existsByUsername(username)).thenReturn(false);
        when(roleRepository.findRoleEntitiesByRoleEnumIn(roles)).thenReturn(List.of(new Role(RoleEnum.USER, Set.of(new Permiso( PermisoEnum.READ )) ) ));
        when(passwordEncoder.encode(password)).thenReturn("encodedPass");

        AuthResponse authResponse = userDetailsServiceImpl.registerUser(registerUserRequest);

        assertNotNull(authResponse);
        assertEquals(username, authResponse.username());
        assertEquals("Usuario registrado con exito!", authResponse.message());
        assertTrue(authResponse.status());

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }












    @Test
    public void testRegisterUser_EmailExistente() {
        String email = "testEmail@test.com";
        AuthRegisterUserRequest registerUserRequest = new AuthRegisterUserRequest("testUser", email, "testPass", "12334", new AuthCreateRoleRequest(List.of("USER") ) );

        when(usuarioRepository.existsByEmail(email)).thenReturn(true);

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userDetailsServiceImpl.registerUser(registerUserRequest);
        });

        assertEquals("Ya existe un usuario con ese email", thrown.getMessage());
    }










    @Test
    public void testRegisterUser_UsernameExistente() {
        String username = "testUser";
        AuthRegisterUserRequest registerUserRequest = new AuthRegisterUserRequest("test", "testEmail@test.com", username, "1234", new AuthCreateRoleRequest(List.of("ADMIN")));


        // Mock para indicar que el nombre de usuario ya existe
        when(usuarioRepository.existsByUsername(username)).thenReturn(true);

        // Mock para devolver un rol existente
        Role existingRole = new Role(RoleEnum.ADMIN, Set.of(new Permiso(PermisoEnum.READ)));
        when(roleRepository.findRoleEntitiesByRoleEnumIn(List.of("ADMIN"))).thenReturn(List.of(existingRole));


        ValidationException thrown = assertThrows(ValidationException.class, () -> {

            userDetailsServiceImpl.registerUser(registerUserRequest);

        });

        assertEquals("Ya existe un usuario con ese username", thrown.getMessage());
    }







    @Test
    public void testRegisterUser_RolesNoExisten() {
        List<String> roles = List.of("ADMIN");

        AuthRegisterUserRequest registerUserRequest = new AuthRegisterUserRequest("testUser", "testEmail@test.com", "testPass","12345",  new AuthCreateRoleRequest(List.of("ADMIN") ));

        when(roleRepository.findRoleEntitiesByRoleEnumIn(roles)).thenReturn(List.of());

        CollectionEmptyException thrown = assertThrows(CollectionEmptyException.class, () -> {
            userDetailsServiceImpl.registerUser(registerUserRequest);
        });

        assertEquals("Los roles especificados no existen", thrown.getMessage());
    }









    // Test for createToken
    @Test
    public void testCreateToken_CredencialesCorrectas() {
        String username = "testUser";
        String password = "testPass";
        Usuario usuario = new Usuario(); // Asume que Usuario implementa UserDetails
        usuario.setPassword("encodedPass");

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, "encodedPass")).thenReturn(true);
        when(jwtUtils.createToken(any(Authentication.class))).thenReturn("jwtToken");

        String token = userDetailsServiceImpl.createToken(username, password);

        assertNotNull(token);
        assertEquals("jwtToken", token);
    }











    @Test
    public void testCreateToken_CredencialesIncorrectas() {
        String username = "testUser";
        String password = "testPass";
        Usuario usuario = new Usuario(); // Asume que Usuario implementa UserDetails
        usuario.setPassword("encodedPass");

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, "encodedPass")).thenReturn(false);

        BadCredentialsException thrown = assertThrows(BadCredentialsException.class, () -> {
            userDetailsServiceImpl.createToken(username, password);
        });

        assertEquals("Password invalido!", thrown.getMessage());
    }












}