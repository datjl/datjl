using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Player : MonoBehaviour
{
    [SerializeField] private float speed = 2f;
    [SerializeField] private float jumpforce = 4f;
    [SerializeField] private LayerMask ground;
    [SerializeField] private int Cherry = 0;
    [SerializeField] private Text cherrytext;

    
    public bool grounded = true, faceright = true, crouch;

    public Rigidbody2D rb;
    public Animator anim;
    private Collider2D coll;

    private enum State { idle, running, jumping, falling, hurt}
    private State state = State.idle;


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
        if (state != State.hurt)
        {
            Controller();
        }

        Controller();
        Stateanimation();
        anim.SetInteger("state", (int)state);
        Health();
    }

    private void Controller()
    {
        float h = Input.GetAxis("Horizontal");
        if (h > 0)
        {
            rb.velocity = new Vector2(speed, rb.velocity.y);
            transform.localScale = new Vector2(1, 1);
        }
        else if (h < 0)
        {
            rb.velocity = new Vector2(-speed, rb.velocity.y);
            transform.localScale = new Vector2(-1, 1);
        }
        else
        {

        }
        if (Input.GetButtonDown("Jump") && coll.IsTouchingLayers(ground))
        {
            Jump();
        }
    }

    private void Jump()
    {
        rb.velocity = new Vector2(rb.velocity.x, jumpforce);
        state = State.jumping;
    }

    public void Health()
    {
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

    void FixedUpdate()
    {
        
    }

    private void OnCollisionEnter2D(Collision2D other)
    {
        if (other.gameObject.tag == "Enemy")
        {
            Enemy enemy = other.gameObject.GetComponent<Enemy>();
            
            if (state == State.falling)
            {
                enemy.Jumpon();
                Jump();
            }
        }
        else
        {
            state = State.hurt;
        }
    }

    private void Stateanimation()
    {

        if (state == State.jumping)
        {
            if(rb.velocity.y < 1f)
            {
                state = State.falling;
            }       
        }       
        else if (state == State.falling)
        {
            if(coll.IsTouchingLayers(ground))
            {
                state = State.idle;
                state = State.running;
            }    
        }
        else if (Mathf.Abs(rb.velocity.x) > 0f)
        {
            state = State.running;
        }
        else if (state == State.hurt)
        {
            if (rb.velocity.x < 1f)
            {
                state = State.idle;
            }
        }
        else
        {
            state = State.idle;
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "collect")
        {
            Destroy(collision.gameObject);
            Cherry += 1;
            cherrytext.text = Cherry.ToString();
        }
    }

    public void Death()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
}