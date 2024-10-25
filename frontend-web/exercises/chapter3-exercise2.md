# Extensions for Automatic Code Generation in Angular

- Angular and AngularJS Plugin → `Settings > Plugins`
- File and Code Templates (Built-in) → `File > New > Edit File Templates`
- Angular CLI Integration (Built-in) → `ng g component <component-name>`
- Prettier (Code Formatter) → `Settings > Prettier`
- CodeLinting and TSLint/ESLint Plugins → `Settings > Plugins`
- Live Templates (Code Snippets) → `Settings > Editor > Live Templates`

#Generating Components via Angular CLI

To generate a new Angular component using the CLI, you can use the following command:

- `ng generate component <component-name>`
- `ng g c <comonent-name>`

### Example

```bash
ng g c user-profile
```

This will create a new folder `user-profile` with the following files:

- `user-profile.component.ts` (Component logic)
- `user-profile.component.html` (Component template)
- `user-profile.component.css` (Component styles)
- `user-profile.component.spec.ts` (Component unit test)

# Differences from Manually Creating Components

- **Manual Creation**:
    - You have to manually create a `.ts`, `.html`, `.css`, and `.spec.ts` file.
    - You need to import the component into the appropriate module (e.g., `app.module.ts`).
    - You may need to manually configure the selector, template, and styles.
- **CLI Generation**:
    - The CLI automatically creates the folder and files for you.
    - The component is automatically declared in the nearest module (e.g., `app.module.ts`).
    - The default structure and file naming convention are handled by the CLI, saving you from repetitive tasks.
    - You can customize the output with options like `-inline-template`, `-inline-style`, and `-skip-tests`.

# Other Files You Can Generate Using Angular CLI

- **Service**:
    
    ```bash
    ng g s <service-name>
    ```
    
    This generates a service file (`.ts`) to manage business logic.
    
- **Module**:
    
    ```bash
    ng g m <module-name>
    ```
    
    This creates a new Angular module.
    
- **Directive**:
    
    ```bash
    ng g d <directive-name>
    ```
    
    This generates a directive file for creating custom directives.
    
- **Pipe**:
    
    ```bash
    ng g p <pipe-name>
    ```
    
    This generates a pipe file for custom data transformation.
    
- **Guard**:
    
    ```bash
    ng g guard <guard-name>
    ```
    
    This generates an Angular route guard.
    
- **Interface**:
    
    ```bash
    ng g interface <interface-name>
    ```
    
    This generates a TypeScript interface.
    
- **Enum**:
    
    ```bash
    ng g enum <enum-name>
    ```
    
    This generates a TypeScript enum.
    
- **Class**:
    
    ```bash
    ng g class <class-name>
    ```
    
    This generates a TypeScript class.
    

Each of these commands can be further customized with flags, such as `--dry-run` (which previews what will happen without making changes), `--skip-tests`, `--flat`, etc.