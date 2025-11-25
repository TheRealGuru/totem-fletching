package com.github.therealguru.totemfletching;

import com.github.therealguru.totemfletching.model.TotemHighlightMode;
import java.awt.Color;
import net.runelite.client.config.*;

@ConfigGroup("totem-fletching")
public interface TotemFletchingConfig extends Config {

    Color RED = Color.decode("#E45F5F");
    Color GREEN = Color.decode("#9CF575");
    Color DEFAULT_TEXT_COLOR = Color.decode("#ddc2ff");

    @ConfigSection(
            name = "Overlay Settings",
            description = "Enable or disable individual overlays",
            position = 0)
    String sectionOverlays = "sectionOverlays";

    @ConfigItem(
            keyName = "renderTotemHighlight",
            name = "Show Totem Highlight",
            description = "Draw the highlight over totems",
            section = sectionOverlays,
            position = 0)
    default TotemHighlightMode renderTotemHighlight() {
        return TotemHighlightMode.OUTLINE;
    }

    @ConfigItem(
            keyName = "totemFillAlpha",
            name = "Totem Fill Opacity",
            section = sectionOverlays,
            description = "Opacity of the filled totem highlight (0-255)",
            position = 1)
    default int totemFillAlpha() {
        return 80;
    }

    @ConfigItem(
            keyName = "renderTotemText",
            name = "Show Totem Text",
            description = "Draw the text overlay over totems for animals and decorations",
            section = sectionOverlays,
            position = 2)
    default boolean renderTotemText() {
        return true;
    }

    @ConfigItem(
            keyName = "renderPoints",
            name = "Show Points Text",
            description = "Draw the text overlay over points",
            section = sectionOverlays,
            position = 3)
    default boolean renderPoints() {
        return true;
    }

    @ConfigItem(
            keyName = "renderEntTrails",
            name = "Show Ent Trails",
            description =
                    "Whether to show the ent trails that need to be stepped on for bonus points",
            section = sectionOverlays,
            position = 4)
    default boolean renderEntTrails() {
        return true;
    }

    @ConfigItem(
            keyName = "highlightCorrectCarvingChoice",
            name = "Highlight Correct Carving Choices",
            description = "Highlight which animals to select in the totem carving interface",
            section = sectionOverlays,
            position = 5)
    default boolean highlightCorrectCarvingChoice() {
        return true;
    }

    @ConfigItem(
            keyName = "maskIncorrectCarvingChoice",
            name = "Mask Incorrect Carving Choices",
            description = "Mask the incorrect animals in the totem carving interface",
            section = sectionOverlays,
            position = 6)
    default boolean maskIncorrectCarvingChoice() {
        return true;
    }

    @ConfigItem(
            keyName = "renderPanel",
            name = "Show widget",
            description = "Show a helper widget when doing Totem fletching",
            section = sectionOverlays,
            position = 7)
    default boolean renderPanel() {
        return false;
    }

    @ConfigItem(
            keyName = "showTotemId",
            name = "Show Totem Id's",
            description =
                    "Use this to help you  determine which totem is which for notification settings",
            section = sectionOverlays,
            position = 8)
    default boolean showTotemId() {
        return false;
    }

    @ConfigSection(
            name = "Color Settings",
            description = "Pick colors for each overlay element",
            position = 1)
    String sectionColors = "sectionColors";

    @ConfigItem(
            keyName = "totemCompleteColor",
            name = "Complete Totem",
            description = "Choose the color used for the highlight on complete totems",
            section = sectionColors,
            position = 0)
    default Color totemCompleteColor() {
        return GREEN;
    }

    @ConfigItem(
            keyName = "totemIncompleteColor",
            name = "Incomplete Totem",
            description = "Choose the color used for the highlight on incomplete totems",
            section = sectionColors,
            position = 1)
    default Color totemIncompleteColor() {
        return RED;
    }

    @ConfigItem(
            keyName = "totemTextColor",
            name = "Totem Text",
            description = "Choose the color used for the text on the totem overlays",
            section = sectionColors,
            position = 2)
    default Color totemTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    @ConfigItem(
            keyName = "pointsTextColor",
            name = "Points Text",
            description = "Choose the color used for the text on the points overlays",
            section = sectionColors,
            position = 3)
    default Color pointsTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    @ConfigItem(
            keyName = "pointsCappedColor",
            name = "Points Tile at Maximum",
            description =
                    "Choose the color used to highlight the points tile if the maximum point total has been reached",
            section = sectionColors,
            position = 4)
    default Color pointsCappedColor() {
        return RED;
    }

    @ConfigItem(
            keyName = "entTrailColor",
            name = "Ent Trail",
            description = "Choose the color used to draw ent trails",
            section = sectionColors,
            position = 5)
    default Color entTrailColor() {
        return Color.decode("#e4fff6");
    }

    @ConfigItem(
            keyName = "carvingHighlightColor",
            name = "Carving Highlight",
            description =
                    "Choose the color used to highlight the correct choices in the carving interface",
            section = sectionColors,
            position = 6)
    default Color carvingHighlightColor() {
        return Color.decode("#9CF575");
    }

    @ConfigItem(
            keyName = "carvingHighlightBorderWidth",
            name = "Carving Highlight Border Width",
            description = "Select the border width for the correct choices",
            section = sectionColors,
            position = 7)
    @Units(Units.PIXELS)
    @Range(min = 1, max = 10)
    default int carvingHighlightBorderWidth() {
        return 2;
    }

    @ConfigItem(
            keyName = "carvingMaskColor",
            name = "Carving Mask",
            description = "Choose the color to mask the incorrect choices in the carving interface",
            section = sectionColors,
            position = 8)
    @Alpha
    default Color carvingMaskColor() {
        return new Color(64, 64, 64, 204);
    }

    @ConfigSection(
            name = "Additional Settings",
            description = "Customize additional accessibility settings",
            closedByDefault = true,
            position = 2)
    String sectionAdditional = "sectionAdditional";

    @ConfigItem(
            keyName = "keepDecoratedText",
            name = "Keep Fully Decorated Text",
            description = "Show the text on a fully decorated totem",
            section = sectionAdditional,
            position = 0)
    default boolean keepDecoratedText() {
        return false;
    }

    @ConfigItem(
            keyName = "showZeroPoints",
            name = "Show Zero Points",
            description = "Show the points text even if the points are zero",
            section = sectionAdditional,
            position = 1)
    default boolean showZeroPoints() {
        return true;
    }

    @ConfigItem(
            keyName = "unbuiltOffset",
            name = "Unbuilt Totem Offset",
            description = "Choose how high the unbuilt totem text will be from the ground",
            section = sectionAdditional,
            position = 2)
    @Units(Units.PIXELS)
    @Range(max = 800)
    default int unbuiltOffset() {
        return 160;
    }

    @ConfigItem(
            keyName = "builtOffset",
            name = "Built Totem Offset",
            description = "Choose how high the built totem text will be from the ground",
            section = sectionAdditional,
            position = 3)
    @Units(Units.PIXELS)
    @Range(max = 800)
    default int builtOffset() {
        return 160;
    }

    @ConfigItem(
            keyName = "pointsOffset",
            name = "Points Offset",
            description = "Choose how high the points text will be from the ground",
            section = sectionAdditional,
            position = 4)
    @Units(Units.PIXELS)
    @Range(max = 800)
    default int pointsOffset() {
        return 16;
    }

    @ConfigSection(
            name = "Notification Settings",
            description = "Enable/Disable Totem Decay Notifications",
            position = 3)
    String sectionNotifications = "sectionNotifications";

    @ConfigItem(
            keyName = "notificationDecayTotem1",
            name = "Notification for Totem 1 Decay",
            description =
                    "This notification will trigger when totem 1 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 0)
    default Notification notificationDecayTotem1() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem2",
            name = "Notification for Totem 2 Decay",
            description =
                    "This notification will trigger when totem 2 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 1)
    default Notification notificationDecayTotem2() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem3",
            name = "Notification for Totem 3 Decay",
            description =
                    "This notification will trigger when totem 3 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 2)
    default Notification notificationDecayTotem3() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem4",
            name = "Notification for Totem 4 Decay",
            description =
                    "This notification will trigger when totem 4 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 3)
    default Notification notificationDecayTotem4() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem5",
            name = "Notification for Totem 5 Decay",
            description =
                    "This notification will trigger when totem 5 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 4)
    default Notification notificationDecayTotem5() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem6",
            name = "Notification for Totem 6 Decay",
            description =
                    "This notification will trigger when totem 6 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 5)
    default Notification notificationDecayTotem6() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem7",
            name = "Notification for Totem 7 Decay",
            description =
                    "This notification will trigger when totem 7 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 6)
    default Notification notificationDecayTotem7() {
        return Notification.OFF;
    }

    @ConfigItem(
            keyName = "notificationDecayTotem8",
            name = "Notification for Totem 8 Decay",
            description =
                    "This notification will trigger when totem 8 has decayed. Enable totem id's to see which totem this is.",
            section = sectionNotifications,
            position = 7)
    default Notification notificationDecayTotem8() {
        return Notification.OFF;
    }
}
