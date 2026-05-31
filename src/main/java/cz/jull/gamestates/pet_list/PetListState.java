package cz.jull.gamestates.pet_list;

import cz.jull.Game;
import cz.jull.gamestates.Button;
import cz.jull.gamestates.State;
import cz.jull.gamestates.StateMethods;
import cz.jull.logic.pet.Pet;
import cz.jull.logic.pet.PetSpecies;
import cz.jull.ui.PetListUI;
import cz.jull.ui.UtilsUI;
import cz.jull.utils.SaveManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Manages the inventory state where players can view their collected pets and swap their active pet.
 */
public class PetListState extends State implements StateMethods {
    // Buttons
    private Button backButton;
    private Button[] buttons;

    private final PetSpecies[] slotSpecies = {
            PetSpecies.CAT, PetSpecies.DOG, PetSpecies.RABBIT, PetSpecies.HAMSTER, PetSpecies.PARROT,
            PetSpecies.OCTOPUS, PetSpecies.SHARK, PetSpecies.TURTLE, PetSpecies.SEAL, PetSpecies.JELLYFISH
    };

    // Pet List
    private final PetListUI petListUI;

    /**
     * Constructs the pet list state.
     *
     * @param game The main game instance.
     */
    public PetListState(Game game) {
        super(game);

        petListUI = new PetListUI();
        loadButtons();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the hover and pressed states of the back button and any other list UI components.
     * </p>
     */
    @Override
    public void update() {
        for (Button button : buttons) {
            if (button != null) {
                button.update();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Draws the inventory grid displaying all pets currently owned by the player,
     * alongside the navigation buttons.
     * </p>
     */
    @Override
    public void draw(Graphics g) {
        petListUI.draw(g, game.getPlayer().getInventory());

        for (Button button : buttons) {
            if (button != null) {
                button.draw(g);
            }
        }
    }

    /**
     * Initializes all interactive buttons for the pet list screen.
     */
    private void loadButtons() {
        backButton = UtilsUI.createBackButton(20, 20);
        buttons = new Button[] {backButton};
    }

    /**
     * Calculates and returns the hit detection rectangle for a specific pet slot in the grid.
     *
     * @param index The index of the slot (0-9).
     * @return A Rectangle representing the boundaries of the pet slot.
     */
    private Rectangle getPetHitbox(int index) {
        int cols = 5;

        int xOffsetStart = 50;
        int yOffsetStart = 215;
        int xSpacing = 100 + 220;
        int ySpacing = 110 + 235;
        int hitboxWidth = 220;
        int hitboxHeight = 235;

        int row = index / cols;
        int col = index % cols;

        int xCorrection = 0;
        if (col == 3 || col == 4) {
            xCorrection = -5;
        }

        int x = xOffsetStart + (col * xSpacing) + xCorrection;
        int y = yOffsetStart + (row * ySpacing);

        return new Rectangle(x, y, hitboxWidth, hitboxHeight);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Detects double clicks on specific pet slots to equip a pet, or single clicks
     * to press down UI navigation buttons.
     * </p>
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {

            if (e.getClickCount() == 2) {
                for (int i = 0; i < 10; i++) {
                    Rectangle hitbox = getPetHitbox(i);

                    if (hitbox.contains(e.getX(), e.getY())) {
                        PetSpecies clickedSpecies = slotSpecies[i];

                        for (Pet ownedPet : game.getPlayer().getInventory()) {
                            if (ownedPet.getSpecies() == clickedSpecies) {
                                game.getPlayer().setEquippedPet(ownedPet);
                                SaveManager.saveGame(game.getPlayer());
                                game.returnToPlaying();
                                return;
                            }
                        }
                        return;
                    }
                }
            }

            for (Button button : buttons) {
                if (button != null && button.isMouseOver()) {
                    button.setMousePressed(true);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes left-click releases to navigate back to the playing state.
     * </p>
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (backButton.isMouseOver() && backButton.isMousePressed()) {
                game.returnToPlaying();
            }

            for (Button button : buttons) {
                if (button != null) {
                    button.setMousePressed(false);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Tracks the mouse position to update the hover state of navigation buttons.
     * </p>
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (Button button : buttons) {
            if (button != null) {
                button.setMouseOver(false);
            }
        }

        for (Button button : buttons) {
            if (button != null && button.getBounds().contains(e.getX(), e.getY())) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Pet List state. Double-clicks are handled in mousePressed.</p>
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Pet List state.</p>
     */
    @Override
    public void KeyPressed(KeyEvent e) {

    }

    /**
     * {@inheritDoc}
     * <p>Unused in the Pet List state.</p>
     */
    @Override
    public void KeyReleased(KeyEvent e) {

    }
}
