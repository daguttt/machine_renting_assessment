package org.example;

import org.example.commands.interfaces.MenuCommand;

public record MenuOption(String text, MenuCommand command) {
}
