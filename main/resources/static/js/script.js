console.log("Script loaded");

let currentTheme = getTheme();
changeTheme();

function changeTheme() {
    document.querySelector("html").classList.add(currentTheme);

    // Set the listener 
    const changeThemeButton = document.querySelector('#theme_button');

    // Update the button text initially
    updateButtonText(changeThemeButton);

    changeThemeButton.addEventListener("click", (event) => {
        const oldTheme = currentTheme;
        console.log("theme changed");

        // Toggle the theme
        currentTheme = (currentTheme === "dark") ? "light" : "dark";

        // Update in local storage 
        setTheme(currentTheme);

        // Remove the current theme 
        document.querySelector('html').classList.remove(oldTheme);

        // Set the current theme 
        document.querySelector('html').classList.add(currentTheme);

        // Update the button text after changing the theme
        updateButtonText(changeThemeButton);
    });
}

      // Function to update the button text
        function updateButtonText(button) {
            button.querySelector("span").textContent = (currentTheme === "light") ? "Dark" : "Light";
        }

     // Set theme to localStorage
       function setTheme(theme) {
         localStorage.setItem("theme", theme);
       }

      // Get theme from localStorage
     function getTheme() {
       let theme = localStorage.getItem("theme");
       return theme ? theme : "light";
     }
