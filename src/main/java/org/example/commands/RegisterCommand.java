package org.example.commands;

import org.example.Menu;
import org.example.commands.interfaces.MenuCommand;

public class RegisterCommand implements MenuCommand {
//    private final UsersController usersController;
//
//    public RegisterCommand(AppState appState, UsersController usersController) {
//        this.appState = appState;
//        this.usersController = usersController;
//    }

    @Override
    public void execute(Menu menu) {
//        String name = InputRequester.requestString("Ingresa tu nombre");
//        String email = InputRequester.requestString("Ingresa tu correo electrónico");
//        String password = InputRequester.requestString("Ingresa tu contraseña");
//
//        List<String> rolesList = Arrays.stream(Roles.values()).map(Enum::name).toList();
//        int roleInt = InputRequester.requestAnIndexFrom(rolesList, "Selecciona un rol");
//        Roles role = Roles.values()[roleInt];
//
//        User user = new User(name, email, password, role);
//        User registeredUser = this.usersController.register(user);
//        appState.setUser(registeredUser);
//        JOptionPane.showMessageDialog(null, "¡Te has registrado correctamente!");
//
//        menu.close();
    }
}
