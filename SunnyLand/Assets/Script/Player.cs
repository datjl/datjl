using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Player : MonoBehaviour
{
    public float fallgravity = 2.5f, upgravity = 2f;
    public bool grounded = true, faceright = true, crouch;
    private LayerMask ground;

    public Rigidbody2D rb;
    public Animator anim;
    private Collider2D coll;

    private enum State { hurt, idle, jumping, running}
    private State state = State.idle;

    public int Cherry = 0;

    public GameObject heart1, heart2, heart3, gameover;
    public static int health;

    // Use this for initialization
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
        anim = GetComponent<Animator>();
        coll = GetComponent<Collider2D>();

        health = 3;
        heart1.gameObject.SetActive(true);
        heart2.gameObject.SetActive(true);
        heart3.gameObject.SetActive(true);
        gameover.gameObject.SetActive(false);

    }

    // Update is called once per frame
    void Update()
    {

        if (rb.velocity.y < 0)
        {
            rb.velocity += Vector2.up * Physics2D.gravity.y * (fallgravity - 1) * Time.deltaTime;
        }
        else if (rb.velocity.y > 0 && !Input.GetKey(KeyCode.Space))
        {
            rb.velocity += Vector2.up * Physics2D.gravity.y * (upgravity - 1) * Time.deltaTime;
        }


        /*     if (state != State.hurt)
               {
                   Movement();
               }
        */

        float h = Input.GetAxis("Horizontal");
        if (h > 0)
        {
            rb.velocity = new Vector2(5, rb.velocity.y);
            transform.localScale = new Vector2(1, 1);
        }
        else if (h < 0)
        {
            rb.velocity = new Vector2(-5, rb.velocity.y);
            transform.localScale = new Vector2(-1, 1);
        }
        else
        {
          
        }

        if (Input.GetButtonDown("Jump"))
        {
            rb.velocity = new Vector2(rb.velocity.x, 10f);
            state = State.jumping;
        }

        velocityState();
        anim.SetInteger("state", (int)state);



        if (health > 3)
            health = 3;

        switch (health)
        {
            case 3:
                heart1.gameObject.SetActive(true);
                heart2.gameObject.SetActive(true);
                heart3.gameObject.SetActive(true);
                break;
            case 2:
                heart1.gameObject.SetActive(true);
                heart2.gameObject.SetActive(true);
                heart3.gameObject.SetActive(false);
                break;
            case 1:
                heart1.gameObject.SetActive(true);
                heart2.gameObject.SetActive(false);
                heart3.gameObject.SetActive(false);
                break;
            case 0:
                heart1.gameObject.SetActive(false);
                heart2.gameObject.SetActive(false);
                heart3.gameObject.SetActive(false);
                gameover.gameObject.SetActive(true);
                Time.timeScale = 0;
                break;
        }    
                

    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "collect")
        {
            Destroy(collision.gameObject);
            Cherry += 1;
        }
    }

    void FixedUpdate()
    {
        


    }

    private void velocityState()
    {
        if (state == State.jumping)
        {

        }
        else if (Mathf.Abs(rb.velocity.x) > 2f)
        {
            state = State.running;
        }
        else
        {
            state = State.idle;
        }

    }

    public void Death()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }

    public void Knockback (float Knockpow, Vector2 Knockdir)
    {
        rb.AddForce(new Vector2(Knockdir.x * -100, Knockdir.y * Knockpow));
    }
}