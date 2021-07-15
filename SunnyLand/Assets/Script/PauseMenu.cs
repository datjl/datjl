using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PauseMenu : MonoBehaviour
{
    private bool Pause = false;
    public GameObject PauseUI;

    // Start is called before the first frame update
    void Start()
    {
        PauseUI.SetActive(false);
    }
    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            Pause = !Pause;
        } 
        if (Pause)
        {
            PauseUI.SetActive(true);
            Time.timeScale = 0;
        }
        if (Pause==false)
        {
            PauseUI.SetActive(false);
            Time.timeScale = 1;
        }
    }
    public void Resume()
    {
        Pause = false;
    }
    public void Restart()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
    public void Quit()
    {
        Application.Quit();
    }
}
