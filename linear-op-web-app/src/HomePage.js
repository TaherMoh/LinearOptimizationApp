import React from 'react';
import clsx from 'clsx';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Solver from './Solver'
import { CsvToHtmlTable } from 'react-csv-to-table';
import example from './Images/example.png';
import homeIcon from './Images/home_icon.png';
import algorithmIcon from './Images/algorithmIcon.png';
import solutionIcon from './Images/solutionIcon.png';
import helpIcon from './Images/helpIcon.png';
import downloadIcon from './Images/download.png';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    backgroundColor: '#759EB8',
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -drawerWidth,
  },
  contentShift: {
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 0,
  },
}));

export default function PersistentDrawerLeft() {
  const classes = useStyles();
  const theme = useTheme();
  const [open, setOpen] = React.useState(true);
  const [component, setComponent] = React.useState(0)
  const [csv, setCsv] = React.useState('')

  const [textAreaValue, setTextAreaValue] = React.useState("")
  const [values, setValues] = React.useState([])
  const [weights, setWeights] = React.useState("0.03, 0.1\n0.03, 0.1\n0.1, 0.25\n0.03, 0.1\n0.1, 0.4\n0.03, 0.1")

  const [numCol, setNumCol] = React.useState(0)
  const [startCol, setStartCol] = React.useState(0)
  const [endCol, setEndCol] = React.useState(0)
  
  const [initialized, setInitialized] = React.useState(false)
  const [uploaded, setUploaded] = React.useState(false)
  const [generated, setGenerated] = React.useState(false)
  const [solved, setSolved] = React.useState(false)
  const [initializeError, setInitializeError] = React.useState(false)
  const [uploadedError, setUploadedError] = React.useState(false)
  const [generatedError, setGeneratedError] = React.useState(false)
  const [solvedError, setSolvedError] = React.useState(false)
  const [selectedCSV, setSelectedCSV] = React.useState('')

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleCsv = (contents) => {
    setCsv(contents);
  }
  
  const handleNumCol = (num) => {
    setNumCol(num);
  }

  const handleStartCol = (num) => {
    setStartCol(num);
  }
  
  const handleEndCol = (num) => {
    setEndCol(num);
  }

  const hanldeInitialized = (input) => {
    setInitialized(input);
  }

  const hanldeUploaded = (input) => {
    setUploaded(input);
  }  

  const hanldeGenerated= (input) => {
    setGenerated(input);
  }  

  const hanldeSolved= (input) => {
    setSolved(input);
  } 
  
  const hanldeInitializedError= (input) => {
    setInitializeError(input);
  }

  const hanldeUploadedError= (input) => {
    setUploadedError(input);
  }

  const hanldeGeneratedError = (input) => {
    setGeneratedError(input);
  }

  const hanldeSolvedError= (input) => {
    setSolvedError(input);
  }

  const hanldeTextAreaValue= (input) => {
    setTextAreaValue(input);
  }

  const hanldeValues= (input) => {
    setValues(input);
  }

  const hanldeWeights= (input) => {
    setWeights(input);
  }

  const handleSelectedCSV= (input) => {
    setSelectedCSV(input);
  }

  const downloadCSVFile = (input) => {
    const element = document.createElement("a");
    const file = new Blob([input], {type: 'text/plain'});
    element.href = URL.createObjectURL(file);
    element.download = "grades.csv";
    document.body.appendChild(element); // Required for this to work in FireFox
    element.click();
  }

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, open && classes.hide)}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            Menu
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="persistent"
        anchor="left"
        open={open}
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <div className={classes.drawerHeader}>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </div>
        <Divider />
        <List>
          {['Home', 'Solver', 'Solution', 'About'].map((text, index) => (
            <ListItem button key={text} onClick={() => setComponent(index)}>
              <ListItemIcon style={{maxWidth: "20%"}}>{
                  index === 0 ? 
                    <img src={homeIcon} alt="home icon" style={{maxWidth: '50%'}}/> 
                    : 
                    index === 1 ? 
                      <img src={algorithmIcon} alt="algorithm icon" style={{maxWidth: '50%'}}/>
                      :
                      index === 2 ? 
                        <img src={solutionIcon} alt="solution icon" style={{maxWidth: '50%'}}/>
                        :
                        index === 3 ? 
                        <img src={helpIcon} alt="help icon" style={{maxWidth: '45%', paddingLeft: '15%'}}/>
                        :
                        null
              }</ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List>
      </Drawer>
      <main
        className={clsx(classes.content, {
          [classes.contentShift]: open,
        })}
      >
        {
            component === 0 ? 
            <div style={{
              fontSize: '1.5em',
              fontFamily: 'Roboto, "Helvetica Neue", Helvetica, Arial, sans-serif',
              fontWeight: '500',
              lineHeight: '1.1',
              padding: '20px',
              width: '100%',
              textAlign: 'left',
              }}>
              <h1>Student Grade Optimizer</h1>
              <Typography>This is an online tool that uses a linear optimization solver to optimize a students final grades</Typography>
              
              <h2>Getting Started</h2>
              <Typography>You can upload any .CSV file in the "Solver" tab to get started and pick your configuration.</Typography>

              <h2>About Student Grade Optimizer</h2>
              <Typography>
                The student grade optimizer (SGO) uses linear optimization to optimize students grades weights.
                
                Given a lower and upper bound for each grade, the optimizer uses Linear Optimization 
                (also known as Linear Programming) to find an optimal weights for grades to get the best overall
                grade for the student.
                
                The SGO converts the user .CSV input combined with the inputed weights to solve for a Linear
                objective function for each students optimal grade. The output is a .CSV file that contains 
                the same headers (coloumn names) as the input, but for each coloumn in the selected there will be 
                the optimal weight calculated by the algorithm to give the student the best grade. 
                Any coloumn that was not selected to be optimized will be ignored by the algorithm and will be 
                the same in the output file. This feature is used to perseve student names, student numbers, dates, etc.
                Empty coloumnns or columns whose value is "-" will be treated as a 0. 
              </Typography>

              <h2>Example</h2>
              <img src={example} alt="example picture" style={{maxWidth: '80%'}}></img>
              <Typography>
                  <strong>
                    Number of columns = 6 <br/>
                    Starting Index = 5 <br/>
                    Ending Index = 10 <br/>

                    Weights: <br/>
                    <div style={{marginLeft: '7%'}}>0.03, 0.1 </div> 
                    <div style={{marginLeft: '7%'}}>0.03, 0.1<br/> </div>
                    <div style={{marginLeft: '7%'}}>0.1, 0.25<br/> </div>
                    <div style={{marginLeft: '7%'}}>0.03, 0.1<br/> </div>
                    <div style={{marginLeft: '7%'}}>0.1, 0.4<br/> </div>
                    <div style={{marginLeft: '7%'}}>0.3, 0.7<br/> </div>
                  </strong>
                
              </Typography>
            </div>
            : null
        }
        
        {
            component === 1 ? 
            <Solver 
              csvHandler={(contents) => handleCsv(contents)} 
              numColHandler={(input) => handleNumCol(input)} 
              startColHandler={(input) => handleStartCol(input)} 
              endColHandler={(input) => handleEndCol(input)} 

              initializedHandler={(input) => hanldeInitialized(input)} 
              uploadedHandler={(input) => hanldeUploaded(input)} 
              generatedHandler={(input) => hanldeGenerated(input)}
              solvedHandler={(input) => hanldeSolved(input)}

              initializedErrorHandler={(input) => hanldeInitializedError(input)} 
              uploadedErrorHandler={(input) => hanldeUploadedError(input)}
              generatedErrorHandler={(input) => hanldeGeneratedError(input)}
              solvedErrorHandler={(input) => hanldeSolvedError(input)}
              selectedCSVHandler={(input) => handleSelectedCSV(input)}

              csv={csv}
              selectedCSV={selectedCSV}
              numCol={numCol}
              startCol={startCol}
              endCol={endCol}
              initialized={initialized}
              uploaded={uploaded}
              generated={generated}
              solved={solved}
              initializeError={initializeError}
              uploadedError={uploadedError}
              generatedError={generatedError}
              solvedError={solvedError}

              textAreaValue={textAreaValue}
              values={values}
              weights={weights}

              textAreaValueHandler={(input) => hanldeTextAreaValue(input)}
              valuesHandler={(input) => hanldeValues(input)}
              weightsHandler={(input) => hanldeWeights(input)}
            />
            : null
        }

        {
          component === 2 ?
            csv === '' ? 
              <h1 style={{marginTop: '5%'}}>No solution yet!</h1>
              :
              <div>
                <div>
                  <img 
                    src={downloadIcon} 
                    onClick={() => downloadCSVFile(csv)}
                    style={{
                      maxWidth: '2.5%',
                      float: 'left',
                      marginRight: '50%',
                      marginLeft: '50%',
                      paddingBottom: '1%',
                    }}
                    />
                </div>
                <div style={{marginTop: '5%'}}>
                  <CsvToHtmlTable
                    data={csv}
                    tableClassName="table striped hover"
                    csvDelimiter=","
                  />
                </div>
              </div>
              
          :
          null
        }

      </main>
    </div>
  );
}
