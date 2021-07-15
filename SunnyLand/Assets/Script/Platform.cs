using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class platform : MonoBehaviour
{
    public void OnllisionStay2D(Collision2D collision)
    {
        if(collision.collider.CompareTag("Player"))
        {
            if (Input.GetKeyDown(KeyCode.S) || Input.GetKeyDown(KeyCode.DownArrow))
                gameObject.GetComponent<Collider2D>().enabled = false;
        }    
    }
    public void OnCollisionExit2D(Collision2D collision)
    {
        if (collision.collider.CompareTag("Player"))
        {
            Invoke("Restore", 0.5f);
        }
    }
    void Restore()
    {
        gameObject.GetComponent<Collider2D>().enabled = true;
    }    
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
