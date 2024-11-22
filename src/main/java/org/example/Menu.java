package org.example;

import org.example.commands.interfaces.MenuCommand;
import org.example.utils.InputRequester;

import java.util.List;

public class Menu {

    public enum MenuStatus {
        OPEN,
        CLOSED
    }

    private static final String DEFAULT_TITLE = "Menu";
    private static final String FOOTER = "************************************************";

    private final List<MenuOption> menuOptions;
    private final String title;
    private final String basePrompt;
    private MenuStatus status;


    public Menu(List<MenuOption> menuOptions) {
        this(menuOptions, DEFAULT_TITLE, "Ingresa una opcion:");
    }


    public Menu(List<MenuOption> menuOptions, String title, String basePrompt) {
        this.menuOptions = menuOptions;
        this.title = title;
        this.basePrompt = basePrompt;
        this.status = MenuStatus.CLOSED;
    }

    public void open() {
        this.status = MenuStatus.OPEN;

        while (this.status == MenuStatus.OPEN) {
            List<String> commandOptionsAsStringList = this.menuOptions.stream().map(MenuOption::text).toList();
            int choice = InputRequester.requestAnIndexFrom(
                    commandOptionsAsStringList,
                    this.basePrompt,
                    (formattedPromptWithOptions) -> String.format("""
                        %s
                        
                        %s
    
                        %s
                        """, this.getFormattedTitle(this.title), formattedPromptWithOptions, FOOTER)
            );

            this.executeSelectedCommand(choice);
        }

    }

    public void close() {
        this.status = MenuStatus.CLOSED;
    }

    private void executeSelectedCommand(int choice) {
        MenuCommand commandToExecute = this.menuOptions.get(choice).command();
        commandToExecute.execute(this);
    }

    private String getFormattedTitle(String baseTitle) {
        final int evenTitleLength = baseTitle.length() % 2 == 0 ? baseTitle.length() : baseTitle.length() + 1;
        final int maxLineLength = FOOTER.length();
        final int spacesAroundTitle = 2;
        final int paddingLength = (maxLineLength - evenTitleLength - spacesAroundTitle) / 2;
        return String.format("%s %s %s", "*".repeat(paddingLength), baseTitle, "*".repeat(paddingLength));
    }
}
