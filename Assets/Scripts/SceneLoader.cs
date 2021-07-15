using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneLoader : MonoBehaviour
{
    // Load first Level
    public void StartFirstLevel()
    {
        SceneManager.LoadScene(1);
    }

    // Load menu scene and destroy game session object
    public void StartMainMenu()
    {
        FindObjectOfType<GameSession>().ResetGameSession();
        SceneManager.LoadScene(0);
    }

    // Quit the application 
    public void Quit()
    {
        Application.Quit();
    }
}
