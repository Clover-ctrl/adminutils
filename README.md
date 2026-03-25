A Minecraft server administration plugin (typically for Spigot / Paper) that gives staff:

Moderation powers (ban, kick, mute, warn)
Player abilities (night vision, no fall damage, etc.)
Quality-of-life tools for managing players

Think of it as a lightweight alternative to big plugins like EssentialsX or LuckPerms, but more focused on admin utilities.
🧪 Admin Abilities (Toggles)

Special powers for staff:

Night Vision
Toggle infinite night vision effect
No Fall Damage
Prevents fall damage entirely
No Drown
Infinite underwater breathing
🧱 How it works internally

A plugin like this usually:

Uses event listeners:
cancel fall damage events
cancel drowning damage
Applies potion effects:
night vision effect loop
Stores data:
muted players
warnings
Hooks into server API:
Spigot/Paper API
![slumberout](https://github.com/user-attachments/assets/78c7f39d-8724-4ead-8510-c0a32374ad56)


